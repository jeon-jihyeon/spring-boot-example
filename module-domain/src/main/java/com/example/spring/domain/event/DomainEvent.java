package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        DomainEventLayer layer,
        DomainEventType type,
        String modelName,
        Long modelId,
        Boolean completed,
        LocalDateTime completedAt,
        LocalDateTime createdAt
) {
    public DomainEvent {
        if (StringUtils.hasText(modelName)) modelName = modelName.toLowerCase();
    }

    private static DomainEvent of(DomainEventLayer layer, DomainEventType type, String modelName, Long modelId) {
        return new DomainEvent(IdGenerator.newId(), layer, type, modelName, modelId, false, null, LocalDateTime.now());
    }

    public static DomainEvent createType(DomainEventLayer layer, String modelName, Long modelId) {
        return DomainEvent.of(layer, DomainEventType.CREATE, modelName, modelId);
    }

    public static DomainEvent updateType(DomainEventLayer layer, String modelName, Long modelId) {
        return DomainEvent.of(layer, DomainEventType.UPDATE, modelName, modelId);
    }

    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, layer, type, modelName, modelId, true, now, createdAt);
    }

    public DomainEvent copy() {
        return new DomainEvent(id, layer, type, modelName, modelId, false, null, createdAt);
    }

    public DomainEvent complete() {
        return complete(LocalDateTime.now());
    }
}