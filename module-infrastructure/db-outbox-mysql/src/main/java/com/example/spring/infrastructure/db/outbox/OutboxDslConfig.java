package com.example.spring.infrastructure.db.outbox;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class OutboxDslConfig {
    @PersistenceContext(unitName = "outboxUnit")
    private EntityManager entityManager;

    @Bean(name = "outboxJPAQueryFactory")
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
