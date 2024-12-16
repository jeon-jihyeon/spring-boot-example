package com.example.spring.infrastructure.db.command.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
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
