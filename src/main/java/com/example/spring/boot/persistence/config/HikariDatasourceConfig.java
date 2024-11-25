package com.example.spring.boot.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class HikariDatasourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource dataSource(@Qualifier("config") HikariConfig config) {
        return new HikariDataSource(config);
    }
}
