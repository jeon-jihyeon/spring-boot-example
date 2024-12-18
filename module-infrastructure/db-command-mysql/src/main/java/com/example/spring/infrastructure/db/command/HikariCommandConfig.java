package com.example.spring.infrastructure.db.command;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.spring.infrastructure.db.command")
@EnableJpaRepositories(basePackages = "com.example.spring.infrastructure.db.command")
class HikariCommandConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.command")
    public HikariConfig commandHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource commandDataSource(@Qualifier("commandHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }
}
