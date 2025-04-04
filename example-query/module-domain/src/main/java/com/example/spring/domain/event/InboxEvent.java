package com.example.spring.domain.event;

import java.time.LocalDateTime;

public record InboxEvent(
        Long id,
        Boolean completed,
        InboxEventType type,
        Long entityId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    public InboxEvent complete() {
        return new InboxEvent(id, true, type, entityId, createdAt, LocalDateTime.now());
    }
}