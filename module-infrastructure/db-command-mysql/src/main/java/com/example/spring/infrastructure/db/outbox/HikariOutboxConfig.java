package com.example.spring.infrastructure.db.outbox;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.spring.infrastructure.db.outbox")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.outbox",
        entityManagerFactoryRef = "outboxEntityManagerFactory",
        transactionManagerRef = "outboxTransactionManager"
)
class HikariOutboxConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.outbox")
    public HikariConfig outboxHikariConfig() {
        return new HikariConfig();
    }

    @Bean
    public HikariDataSource outboxDataSource(@Qualifier("outboxHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean outboxEntityManagerFactory(@Qualifier("outboxDataSource") HikariDataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.outbox");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(new Properties());
        return em;
    }

    @Bean
    public PlatformTransactionManager outboxTransactionManager(
            @Qualifier("outboxDataSource") HikariDataSource dataSource,
            @Qualifier("outboxEntityManagerFactory") EntityManagerFactory entityManagerFactory
    ) {
        JpaTransactionManager tm = new JpaTransactionManager(entityManagerFactory);
        tm.setDataSource(dataSource);
        return tm;
    }
}
