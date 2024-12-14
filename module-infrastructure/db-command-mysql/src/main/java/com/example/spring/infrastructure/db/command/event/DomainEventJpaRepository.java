package com.example.spring.infrastructure.db.command.event;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DomainEventJpaRepository extends JpaRepository<DomainEventEntity, Long> {
}
