package com.example.spring.infrastructure.db.inbox;

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
@EntityScan(basePackages = "com.example.spring.infrastructure.db.inbox")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.inbox",
        entityManagerFactoryRef = "inboxEntityManagerFactory",
        transactionManagerRef = "inboxTransactionManager"
)
class InboxDbConfig {

    @Bean(name = "inboxHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.inbox")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Bean(name = "inboxDataSource")
    public HikariDataSource dataSource(@Qualifier("inboxHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean(name = "inboxEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("inboxDataSource") HikariDataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.inbox");
        em.setPersistenceUnitName("inboxUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Primary
    @Bean(name = "inboxEntityManager")
    public EntityManager entityManager(@Qualifier("inboxEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean(name = "inboxTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("inboxEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean(name = "inboxJPAQueryFactory")
    public JPAQueryFactory jpaQueryFactory(@Qualifier("inboxEntityManager") EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}
