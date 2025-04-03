package com.example.spring.application.batch;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jdbc.support.JdbcTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class ContextTestConfig {
    @Bean
    @ConfigurationProperties("spring.datasource.outbox")
    public HikariConfig commandHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource commandDataSource(HikariConfig commandHikariConfig) {
        return new HikariDataSource(commandHikariConfig);
    }

    @Bean
    public PlatformTransactionManager commandTransactionManager(DataSource commandDataSource) {
        return new JdbcTransactionManager(commandDataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource commandDataSource) {
        return new JdbcTemplate(commandDataSource);
    }

    @Bean
    public DataSourceInitializer commandInitializer(DataSource commandDataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema.sql"));
        // resourceDatabasePopulator.addScript(new ClassPathResource("data.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(commandDataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }

    @Bean
    public DataSourceInitializer metaInitializer(DataSource metaDataSource) {
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("org/springframework/batch/core/schema-h2.sql"));

        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(metaDataSource);
        dataSourceInitializer.setDatabasePopulator(resourceDatabasePopulator);
        return dataSourceInitializer;
    }
}
