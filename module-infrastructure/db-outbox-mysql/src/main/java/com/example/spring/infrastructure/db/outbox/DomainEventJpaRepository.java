package com.example.spring.infrastructure.db.outbox;

import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;

@PersistenceContext(unitName = "outboxEntityManager")
public interface DomainEventJpaRepository extends JpaRepository<DomainEventEntity, Long> {
}
