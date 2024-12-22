package com.example.spring.infrastructure.db.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.Properties;

@Configuration
@EntityScan(basePackages = "com.example.spring.infrastructure.db.outbox")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.outbox",
        entityManagerFactoryRef = "outboxEntityManagerFactory",
        transactionManagerRef = "outboxTransactionManager"
)
class OutboxDbConfig {

    @Bean(name = "outboxHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.outbox")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean(name = "outboxDataSource")
    public HikariDataSource dataSource(@Qualifier("outboxHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean(name = "outboxEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("outboxDataSource") HikariDataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.outbox");
        em.setPersistenceUnitName("outboxUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Primary
    @Bean(name = "outboxEntityManager")
    public EntityManager entityManager(@Qualifier("outboxEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean(name = "outboxTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("outboxEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean(name = "outboxJPAQueryFactory")
    public JPAQueryFactory jpaQueryFactory(@Qualifier("outboxEntityManager") EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
