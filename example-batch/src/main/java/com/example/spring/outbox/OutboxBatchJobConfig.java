package com.example.spring.outbox;

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
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.support.CompositeItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class OutboxBatchJobConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OutboxEventBatchProducer messageProducer;
    private final OutboxBatchJobProperties properties;

    public OutboxBatchJobConfig(OutboxEventBatchProducer messageProducer, OutboxBatchJobProperties properties) {
        this.messageProducer = messageProducer;
        this.properties = properties;
    }

    @Bean
    public Job outboxBatchJob(JobRepository jobRepository, Step outboxBatchStep) {
        return new JobBuilder(properties.name(), jobRepository).start(outboxBatchStep).build();
    }

    @Bean
    public Step outboxBatchStep(
            JobRepository jobRepository,
            ItemProcessor<OutboxEvent, OutboxEvent> itemProcessor,
            CompositeItemWriter<OutboxEvent> compositeItemWriter,
            ItemReader<OutboxEvent> itemReader,
            PlatformTransactionManager commandTransactionManager
    ) {
        return new StepBuilder("step", jobRepository)
                .<OutboxEvent, OutboxEvent>chunk(properties.chunkSize(), commandTransactionManager)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(compositeItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<OutboxEvent, OutboxEvent> itemProcessor(@Value("#{jobParameters['now']}") LocalDateTime now) {
        return model -> model.complete(now);
    }

    @Bean
    public CompositeItemWriter<OutboxEvent> compositeItemWriter(ItemWriter<OutboxEvent> itemWriter, ItemWriter<OutboxEvent> messageProducer) {
        return new CompositeItemWriter<>(Arrays.asList(itemWriter, messageProducer));
    }

    @Bean
    public ItemWriter<OutboxEvent> itemWriter(DataSource commandDataSource) {
        return new JdbcBatchItemWriterBuilder<OutboxEvent>()
                .dataSource(commandDataSource)
                .sql("UPDATE outbox_events SET completed=:completed, completed_at=:completedAt, updated_at=:completedAt WHERE id=:id;")
                .beanMapped()
                .assertUpdates(true)
                .build();
    }

    @Bean
    public ItemWriter<OutboxEvent> messageProducer() {
        return chunk -> {
            final List<OutboxEvent> events = new ArrayList<>();
            for (OutboxEvent e : chunk) events.add(e);
            if (!events.isEmpty()) messageProducer.sendBatch(events);
        };
    }
}
