package com.example.spring.infrastructure.db.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainEventJpaRepository extends JpaRepository<DomainEventEntity, Long> {
}
