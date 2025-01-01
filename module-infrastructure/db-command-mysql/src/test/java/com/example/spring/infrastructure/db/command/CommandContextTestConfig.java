package com.example.spring.infrastructure.db.command;

import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.domain.player.PlayerCommandMessageService;
import com.example.spring.domain.team.TeamCommandMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Configuration
public class CommandContextTestConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DomainEventOutbox outbox = event -> log.debug("[Outbox] {}", event);
    private final MessageProducer producer = event -> log.debug("[Message] {}", event);

    @Bean
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
        return new PlayerCommandMessageService(outbox, producer);
    }
}
