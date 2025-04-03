package com.example.spring.batch;


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
    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, true, type, queueName, modelId, createdAt, now);
    }
}