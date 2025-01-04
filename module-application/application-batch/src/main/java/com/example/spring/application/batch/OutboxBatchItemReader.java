package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class OutboxBatchItemReader {
    private final OutboxBatchJobProperties properties;

    public OutboxBatchItemReader(OutboxBatchJobProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JdbcPagingItemReader<DomainEvent> itemReader(DataSource commandDataSource) {
        return new JdbcPagingItemReaderBuilder<DomainEvent>()
                .name("jdbcPagingItemReader")
                .fetchSize(properties.chunkSize())
                .dataSource(commandDataSource)
                .selectClause("*")
                .fromClause("from outbox_events")
                .whereClause("where completed = false")
                .sortKeys(Map.of("created_at", Order.DESCENDING))
                .rowMapper(new DomainEventRowMapper())
                .build();
    }
}
