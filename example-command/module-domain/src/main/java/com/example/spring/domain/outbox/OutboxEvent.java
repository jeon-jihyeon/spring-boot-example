package com.example.spring.domain.outbox;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;

public record OutboxEvent(
        Long id,
        Boolean completed,
        OutboxEventType type,
        Long entityId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    public static OutboxEvent of(OutboxEventType type, Long modelId) {
        return new OutboxEvent(IdGenerator.newId(), false, type, modelId, LocalDateTime.now(), null);
    }

    public OutboxEvent complete() {
        return new OutboxEvent(id, true, type, entityId, createdAt, LocalDateTime.now());
    }
}