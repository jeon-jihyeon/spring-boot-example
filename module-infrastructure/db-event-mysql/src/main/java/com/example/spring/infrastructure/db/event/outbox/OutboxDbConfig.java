package com.example.spring.infrastructure.db.event.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
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

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EntityScan(basePackages = "com.example.spring.infrastructure.db.event.outbox")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.event.outbox",
        entityManagerFactoryRef = "outboxEntityManagerFactory",
        transactionManagerRef = "outboxTransactionManager"
)
class OutboxDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.outbox")
    public HikariConfig outboxConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource outboxDataSource(@Qualifier("outboxConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean outboxEntityManagerFactory(@Qualifier("outboxDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.event.outbox");
        em.setPersistenceUnitName("outboxUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public EntityManager outboxEntityManager(
            @Qualifier("outboxEntityManagerFactory") EntityManagerFactory factory,
            OutboxPostUpdateEventListener postUpdateListener
    ) {
        SessionFactoryImpl sessionFactory = factory.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
        if (registry != null) registry.getEventListenerGroup(EventType.POST_UPDATE).appendListener(postUpdateListener);
        return sessionFactory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager outboxTransactionManager(@Qualifier("outboxEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public JPAQueryFactory outboxJPAQueryFactory(@Qualifier("outboxEntityManager") EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
