package com.example.spring.infrastructure.db.command;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class CommandDslConfig {
    @PersistenceContext(unitName = "commandUnit")
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory commandQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
