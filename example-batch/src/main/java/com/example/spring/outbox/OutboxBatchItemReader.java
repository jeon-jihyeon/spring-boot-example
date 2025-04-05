package com.example.spring.outbox;

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
    public JdbcPagingItemReader<OutboxEvent> itemReader(DataSource commandDataSource) {
        return new JdbcPagingItemReaderBuilder<OutboxEvent>()
                .name("jdbcPagingItemReader")
                .fetchSize(properties.chunkSize())
                .dataSource(commandDataSource)
                .selectClause("*")
                .fromClause("from outbox_events")
                .whereClause("where completed = false")
                .sortKeys(Map.of("created_at", Order.DESCENDING))
                .rowMapper(new OutboxEventRowMapper())
                .build();
    }
}
