package com.example.spring.outbox;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
class CommandDbConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.command")
    public HikariConfig commandConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource commandDataSource(HikariConfig commandConfig) {
        return new HikariDataSource(commandConfig);
    }

    @Bean
    public PlatformTransactionManager commandTransactionManager(DataSource commandDataSource) {
        return new JdbcTransactionManager(commandDataSource);
    }
}
