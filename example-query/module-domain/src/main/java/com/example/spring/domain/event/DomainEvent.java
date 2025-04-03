package com.example.spring.domain.event;

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
    public DomainEvent complete() {
        return new DomainEvent(id, true, type, queueName, modelId, createdAt, LocalDateTime.now());
    }
}