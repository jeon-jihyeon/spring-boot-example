package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        Boolean completed,
        DomainEventType type,
        String queueName,
        Long modelId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    public static DomainEvent of(DomainEventType type, String queueName, Long modelId) {
        return new DomainEvent(
                IdGenerator.newId(),
                false,
                type,
                queueName,
                modelId,
                LocalDateTime.now(),
                null
        );
    }

    public static DomainEvent createType(String queueName, Long modelId) {
        return DomainEvent.of(DomainEventType.CREATE, queueName, modelId);
    }

    public static DomainEvent updateType(String queueName, Long modelId) {
        return DomainEvent.of(DomainEventType.UPDATE, queueName, modelId);
    }

    public static DomainEvent deleteType(String queueName, Long modelId) {
        return DomainEvent.of(DomainEventType.DELETE, queueName, modelId);
    }

    public DomainEvent complete() {
        return complete(LocalDateTime.now());
    }

    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, true, type, queueName, modelId, createdAt, now);
    }

    public DomainEvent copy() {
        return new DomainEvent(id, false, type, queueName, modelId, LocalDateTime.now(), null);
    }
}