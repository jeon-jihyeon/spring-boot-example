package com.example.spring.application.batch;

import com.example.spring.infrastructure.db.outbox.DomainEventEntity;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "metaDataSource", transactionManagerRef = "outboxTransactionManager")
public class OutboxBatchConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BatchThreadPoolProperties threadPoolProperties;
    @Value("${spring.batch.job.name}")
    private String jobName;
    @Value("${spring.batch.chunk-size}")
    private Integer chunkSize;

    public OutboxBatchConfig(BatchThreadPoolProperties threadPoolProperties) {
        this.threadPoolProperties = threadPoolProperties;
    }

    @Bean(name = "metaHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.meta")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean(name = "metaDataSource")
    public HikariDataSource dataSource(@Qualifier("metaHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public TaskExecutor getBatchAsyncExecutor(BatchThreadPoolProperties properties) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("batch-async-");
        executor.setThreadGroupName("batch-async-group");
        executor.setCorePoolSize(properties.coreSize / 2);
        executor.setMaxPoolSize(properties.maxSize / 2);
        executor.setQueueCapacity(properties.queueCapacity / 2);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(10);
        executor.initialize();
        return executor;
    }

    @Bean
    public Job outboxBatchJob(JobRepository jobRepository, Step outboxBatchStep) {
        return new JobBuilder(jobName, jobRepository).start(outboxBatchStep).build();
    }

    @Bean
    public Step outboxBatchStep(
            JobRepository jobRepository,
            BatchThreadPoolProperties properties,
            @Qualifier("outboxDataSource") HikariDataSource dataSource,
            @Qualifier("outboxTransactionManager") PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("step", jobRepository)
                .<DomainEventEntity, DomainEventEntity>chunk(chunkSize, transactionManager)
                .reader(pagingItemReader(dataSource))
                .writer(batchItemWriter(dataSource))
                .taskExecutor(getBatchAsyncExecutor(properties))
                .build();
    }

    @Bean
    public JdbcPagingItemReader<DomainEventEntity> pagingItemReader(HikariDataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<DomainEventEntity>()
                .name("jdbcPagingItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .selectClause("*")
                .fromClause("from domain_events")
                .whereClause("where published = :published")
                .rowMapper(new BeanPropertyRowMapper<>(DomainEventEntity.class))
                .parameterValues(Map.of("published", false))
                .sortKeys(Map.of("created_at", Order.DESCENDING))
                .build();
    }

    @Bean
    public ItemWriter<DomainEventEntity> batchItemWriter(HikariDataSource dataSource) {
        // TODO: send message
        return new JdbcBatchItemWriterBuilder<DomainEventEntity>()
                .dataSource(dataSource)
                .sql("update domain_events set published = :published, published_at = :published_at where id = :id")
                .beanMapped()
                .build();
    }

    @ConfigurationProperties(prefix = "spring.batch.task.execution.pool")
    public record BatchThreadPoolProperties(Integer coreSize, Integer maxSize, Integer queueCapacity) {
    }
}
