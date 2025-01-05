package com.example.spring.domain.event.model;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;
import java.util.List;

public record DomainEvent(
        Long id,
        Boolean completed,
        DomainEventType type,
        String queueName,
        Long modelId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    private static DomainEvent of(DomainEventType type, String queueName, Long modelId, LocalDateTime createdAt) {
        return new DomainEvent(IdGenerator.newId(), false, type, queueName, modelId, createdAt, null);
    }

    private static DomainEvent of(DomainEventType type, String queueName, Long modelId) {
        return of(type, queueName, modelId, LocalDateTime.now());
    }

    public static DomainEvent createType(String queueName, Long modelId) {
        return DomainEvent.of(DomainEventType.CREATE, queueName, modelId);
    }

    public static DomainEvent updateType(String queueName, Long modelId) {
        return DomainEvent.of(DomainEventType.UPDATE, queueName, modelId);
    }

    public static List<DomainEvent> updateTypes(String queueName, List<Long> modelIds) {
        final LocalDateTime now = LocalDateTime.now();
        return modelIds.stream().map(modelId -> DomainEvent.of(DomainEventType.UPDATE, queueName, modelId, now)).toList();
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

    public DomainEvent copyInbox() {
        return new DomainEvent(id, false, type, queueName, modelId, LocalDateTime.now(), null);
    }
}