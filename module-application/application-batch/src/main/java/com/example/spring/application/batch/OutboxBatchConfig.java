package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEventBatchService;
import com.example.spring.infrastructure.db.outbox.OutboxEventEntity;
import com.example.spring.infrastructure.db.outbox.OutboxEventJpaMapper;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableBatchProcessing(dataSourceRef = "metaDataSource", transactionManagerRef = "outboxTransactionManager")
public class OutboxBatchConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final BatchThreadPoolProperties properties;
    private final DomainEventBatchService service;
    private final OutboxEventJpaMapper mapper;

    @Value("${spring.batch.job.name}")
    private String jobName;

    @Value("${spring.batch.chunk-size}")
    private Integer chunkSize;

    public OutboxBatchConfig(
            BatchThreadPoolProperties properties,
            DomainEventBatchService service,
            OutboxEventJpaMapper mapper) {
        this.properties = properties;
        this.service = service;
        this.mapper = mapper;
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
    public TaskExecutor getBatchAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("batch-async-");
        executor.setThreadGroupName("batch-async-group");
        executor.setCorePoolSize(properties.coreSize);
        executor.setMaxPoolSize(properties.maxSize);
        executor.setQueueCapacity(properties.queueCapacity);
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
    @JobScope
    public Step outboxBatchStep(
            JobRepository jobRepository,
            ItemWriter<OutboxEventEntity> outboxItemWriter,
            JdbcPagingItemReader<OutboxEventEntity> outboxPagingItemReader,
            @Qualifier("outboxTransactionManager") PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("step", jobRepository)
                .<OutboxEventEntity, OutboxEventEntity>chunk(chunkSize, transactionManager)
                .reader(outboxPagingItemReader)
                .writer(outboxItemWriter)
                .taskExecutor(getBatchAsyncExecutor())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<OutboxEventEntity> outboxPagingItemReader(@Qualifier("outboxDataSource") HikariDataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<OutboxEventEntity>()
                .name("jdbcPagingItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .selectClause("*")
                .fromClause("from outbox.domain_events")
                .whereClause("where completed = :completed")
                .rowMapper(new BeanPropertyRowMapper<>(OutboxEventEntity.class))
                .parameterValues(Map.of("completed", false))
                .sortKeys(Map.of("created_at", Order.DESCENDING))
                .build();
    }

    @Bean
    @StepScope
    public ItemWriter<OutboxEventEntity> outboxItemWriter(@Value("#{jobParameters['now']}") LocalDateTime now) {
        return chunk -> {
            final List<OutboxEventEntity> entities = new ArrayList<>();
            for (OutboxEventEntity e : chunk) entities.add(e);
            service.invoke(entities.stream().map(e -> mapper.toDomain(e).complete(now)).toList(), now);
        };
    }

    @ConfigurationProperties(prefix = "spring.batch.task.execution.pool")
    public record BatchThreadPoolProperties(Integer coreSize, Integer maxSize, Integer queueCapacity) {
    }
}
