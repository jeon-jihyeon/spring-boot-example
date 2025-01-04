package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class OutboxBatchItemReaderTest extends BaseUnitTest {
    private JdbcTemplate jdbcTemplate;
    private JdbcPagingItemReader<DomainEvent> reader;

    @BeforeEach
    public void setUp() {
        var context = new AnnotationConfigApplicationContext(TestDataSourceConfiguration.class);
        var dataSource = (DataSource) context.getBean("outboxDataSource");
        jdbcTemplate = new JdbcTemplate(dataSource);
        reader = new OutboxBatchItemReader(new OutboxBatchJobProperties("batch", 100)).itemReader(dataSource);
    }

    @Test
    public void testReader() throws Exception {
        final LocalDateTime now = LocalDateTime.now();
        final String insert = "INSERT INTO outbox_events VALUES (?, ?, 'CREATE', 'model', ?, NULL, ?, ?)";
        for (int i = 1; i < 11; i++) jdbcTemplate.update(insert, i, false, i, now, now);
        for (int i = 11; i < 21; i++) jdbcTemplate.update(insert, i, true, i, now, now);

        DomainEvent e;
        reader.afterPropertiesSet();
        for (int i = 1; i < 11; i++) {
            e = reader.read();
            assertThat(e).isNotNull();
            assertThat(e.id()).isEqualTo(i);
            assertThat(e.completed()).isFalse();
        }
    }

    @Configuration
    public static class TestDataSourceConfiguration {
        @Bean(name = "outboxDataSource")
        public DataSource dataSource() {
            EmbeddedDatabaseFactory factory = new EmbeddedDatabaseFactory();
            factory.setDatabaseType(EmbeddedDatabaseType.H2);
            return factory.getDatabase();
        }

        @Bean
        public DataSourceInitializer outboxInitializer(DataSource outboxDataSource) {
            ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
            resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
            // resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));

            DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
            dataSourceInitializer.setDataSource(outboxDataSource);
            dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
            return dataSourceInitializer;
        }
    }
}