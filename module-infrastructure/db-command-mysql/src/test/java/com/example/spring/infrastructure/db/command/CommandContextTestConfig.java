package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class CommandContextTestConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DomainEventOutboxRepository outbox = new DomainEventOutboxRepository() {
        @Override
        public void save(DomainEvent event) {
            log.debug("[Outbox] {}", event);
        }

        @Override
        public void createAll(List<DomainEvent> events) {
            events.forEach(event -> log.debug("[Outbox] {}", event));
        }
    };
    private final MessageProducer producer = event -> log.debug("[Message] {}", event);
    private final MessageBatchProducer batchProducer = events -> log.debug("[BatchMessage] {}", events);
    private final DomainEventInboxRepository inbox = new DomainEventInboxRepository() {
        @Override
        public void save(DomainEvent event) {
            log.debug("[Inbox] {}", event);
        }

        @Override
        public boolean exists(Long id) {
            log.debug("[Inbox] {}", id);
            return false;
        }
    };

    @Bean
    @Profile("test")
    public DataSourceInitializer commandInitializer(@Qualifier("commandDataSource") DataSource dataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("command.sql"));
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public QueryNewInboxService newInboxService() {
        return new QueryNewInboxService(inbox);
    }
}
