package com.example.spring.infrastructure.db.event.inbox;

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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EntityScan(basePackages = "com.example.spring.infrastructure.db.event.inbox")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.event.inbox",
        entityManagerFactoryRef = "inboxEntityManagerFactory",
        transactionManagerRef = "inboxTransactionManager"
)
class InboxDbConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.inbox")
    public HikariConfig inboxConfig() {
        return new HikariConfig();
    }

    @Bean
    public DataSource inboxDataSource(@Qualifier("inboxConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean inboxEntityManagerFactory(@Qualifier("inboxDataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);
        vendorAdapter.setShowSql(true);

        Properties properties = new Properties();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.event.inbox");
        em.setPersistenceUnitName("inboxUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(properties);
        return em;
    }

    @Bean
    public EntityManager inboxEntityManager(@Qualifier("inboxEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Bean
    public PlatformTransactionManager inboxTransactionManager(@Qualifier("inboxEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Bean
    public JPAQueryFactory inboxJPAQueryFactory(@Qualifier("inboxEntityManager") EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
