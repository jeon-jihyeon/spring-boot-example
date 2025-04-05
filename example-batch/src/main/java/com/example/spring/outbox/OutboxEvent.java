package com.example.spring.outbox;


import java.time.LocalDateTime;

public record OutboxEvent(
        Long id,
        Boolean completed,
        OutboxEventType type,
        Long entityId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    public OutboxEvent complete(LocalDateTime now) {
        return new OutboxEvent(id, true, type, entityId, createdAt, now);
    }
}