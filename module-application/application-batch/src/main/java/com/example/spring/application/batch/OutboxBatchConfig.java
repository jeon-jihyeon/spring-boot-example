package com.example.spring.application.batch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing(dataSourceRef = "metaDataSource", transactionManagerRef = "metaTransactionManager")
public class OutboxBatchConfig {
    @Bean(name = "metaHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.meta")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean(name = "metaDataSource")
    public HikariDataSource dataSource(@Qualifier("metaHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean(name = "metaTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("metaDataSource") DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }
}
