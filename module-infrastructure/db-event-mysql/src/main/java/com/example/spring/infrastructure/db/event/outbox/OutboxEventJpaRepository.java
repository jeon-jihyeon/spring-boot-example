package com.example.spring.infrastructure.db.event.outbox;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OutboxEventJpaRepository extends JpaRepository<OutboxEventEntity, Long> {
    Optional<OutboxEventEntity> findByModelNameAndModelId(String modelName, Long modelId);
}
