package com.example.spring.infrastructure.db.command;

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
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "com.example.spring.infrastructure.db.command")
@EnableJpaRepositories(
        basePackages = "com.example.spring.infrastructure.db.command",
        entityManagerFactoryRef = "commandEntityManagerFactory",
        transactionManagerRef = "commandTransactionManager"
)
class HikariCommandConfig {

    @Primary
    @Bean(name = "commandHikariConfig")
    @ConfigurationProperties(prefix = "spring.datasource.command")
    public HikariConfig config() {
        return new HikariConfig();
    }

    @Primary
    @Bean(name = "commandDataSource")
    public HikariDataSource dataSource(@Qualifier("commandHikariConfig") HikariConfig config) {
        return new HikariDataSource(config);
    }

    @Primary
    @Bean(name = "commandEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("commandDataSource") HikariDataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com.example.spring.infrastructure.db.command");
        em.setPersistenceUnitName("commandUnit");
        em.setPersistenceProvider(new HibernatePersistenceProvider());
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Primary
    @Bean(name = "commandEntityManager")
    public EntityManager entityManager(@Qualifier("commandEntityManagerFactory") EntityManagerFactory factory) {
        return factory.createEntityManager();
    }

    @Primary
    @Bean(name = "commandTransactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("commandEntityManagerFactory") EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }
}
