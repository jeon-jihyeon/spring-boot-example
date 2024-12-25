package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventProducer;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Configuration
public class OutboxBatchJobConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DomainEventProducer producer;

    @Value("${spring.batch.job.name}")
    private String jobName;

    @Value("${spring.batch.chunk-size}")
    private Integer chunkSize;

    public OutboxBatchJobConfig(DomainEventProducer producer) {
        this.producer = producer;
    }

    @Bean
    public Job outboxBatchJob(JobRepository jobRepository, Step outboxBatchStep) {
        return new JobBuilder(jobName, jobRepository).start(outboxBatchStep).build();
    }

    @Bean
    public Step outboxBatchStep(
            JobRepository jobRepository,
            ItemProcessor<DomainEvent, DomainEvent> itemProcessor,
            CompositeItemWriter<DomainEvent> compositeItemWriter,
            ItemReader<DomainEvent> itemReader,
            @Qualifier("outboxTransactionManager") PlatformTransactionManager transactionManager
    ) {
        return new StepBuilder("step", jobRepository)
                .<DomainEvent, DomainEvent>chunk(chunkSize, transactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(compositeItemWriter)
                .build();
    }

    @Bean
    public ItemReader<DomainEvent> itemReader(@Qualifier("outboxDataSource") HikariDataSource dataSource) {
        return new JdbcPagingItemReaderBuilder<DomainEvent>()
                .name("jdbcPagingItemReader")
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .selectClause("*")
                .fromClause("from outbox_events")
                .whereClause("where completed = :completed")
                .rowMapper(new DomainEventRowMapper())
                .parameterValues(Map.of("completed", false))
                .sortKeys(Map.of("created_at", Order.DESCENDING))
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<DomainEvent, DomainEvent> itemProcessor(@Value("#{jobParameters['now']}") LocalDateTime now) {
        return model -> model.complete(now);
    }

    @Bean
    public CompositeItemWriter<DomainEvent> compositeItemWriter(ItemWriter<DomainEvent> itemWriter, ItemWriter<DomainEvent> messageProducer) {
        return new CompositeItemWriter<>(Arrays.asList(itemWriter, messageProducer));
    }

    @Bean
    public ItemWriter<DomainEvent> itemWriter(@Qualifier("outboxDataSource") HikariDataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<DomainEvent>()
                .dataSource(dataSource)
                .sql("UPDATE outbox_events SET completed=:completed, completed_at=:completedAt, updated_at=:completedAt WHERE id=:id;")
                .beanMapped()
                .assertUpdates(true)
                .build();
    }

    @Bean
    public ItemWriter<DomainEvent> messageProducer() {
        return chunk -> {
            final List<DomainEvent> models = new ArrayList<>();
            for (DomainEvent e : chunk) models.add(e);
            producer.sendBatch(models);
        };
    }
}
