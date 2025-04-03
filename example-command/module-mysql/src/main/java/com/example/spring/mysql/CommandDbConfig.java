package com.example.spring.mysql;

import com.example.spring.mysql.outbox.OutboxPostInsertEventListener;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.spring.mysql")
@EnableJpaRepositories(
        basePackages = "com.example.spring.mysql",
        entityManagerFactoryRef = "commandEntityManagerFactory",
        transactionManagerRef = "commandTransactionManager"
)
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
    public LocalContainerEntityManagerFactoryBean commandEntityManagerFactory(DataSource commandDataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(commandDataSource);
        em.setPackagesToScan("com.example.spring.mysql");
        em.setPersistenceUnitName("commandUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public EntityManager commandEntityManager(
            EntityManagerFactory commandEntityManagerFactory,
            OutboxPostInsertEventListener outboxPostInsertEventListener
    ) {
        SessionFactoryImpl sessionFactory = commandEntityManagerFactory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        if (registry != null) {
            registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(outboxPostInsertEventListener);
        }
        return sessionFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager commandTransactionManager(EntityManagerFactory commandEntityManagerFactory) {
        return new JpaTransactionManager(commandEntityManagerFactory);
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource commandDataSource) {
        return new JdbcTemplate(commandDataSource);
    }
}
