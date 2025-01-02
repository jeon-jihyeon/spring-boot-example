package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.command.player.PlayerCommandMessageService;
import com.example.spring.domain.command.team.TeamCommandMessageService;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageBatchProducer;
import com.example.spring.domain.event.MessageProducer;
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
    private final DomainEventOutbox outbox = new DomainEventOutbox() {
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
    public TeamCommandMessageService teamCommandMessageService() {
        return new TeamCommandMessageService(outbox, producer);
    }

    @Bean
    public PlayerCommandMessageService playerCommandMessageService() {
        return new PlayerCommandMessageService(outbox, producer, batchProducer);
    }
}
