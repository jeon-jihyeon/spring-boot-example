package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        Boolean completed,
        DomainEventType type,
        String modelName,
        Long modelId,
        LocalDateTime createdAt,
        LocalDateTime completedAt
) {
    public DomainEvent {
        if (StringUtils.hasText(modelName)) modelName = modelName.toLowerCase();
    }

    private static DomainEvent of(DomainEventType type, String modelName, Long modelId) {
        return new DomainEvent(
                IdGenerator.newId(),
                false,
                type,
                modelName,
                modelId,
                LocalDateTime.now(),
                null
        );
    }

    public static DomainEvent createType(String modelName, Long modelId) {
        return DomainEvent.of(DomainEventType.CREATE, modelName, modelId);
    }

    public static DomainEvent updateType(String modelName, Long modelId) {
        return DomainEvent.of(DomainEventType.UPDATE, modelName, modelId);
    }

    public static DomainEvent deleteType(String modelName, Long modelId) {
        return DomainEvent.of(DomainEventType.DELETE, modelName, modelId);
    }

    public DomainEvent complete() {
        return complete(LocalDateTime.now());
    }

    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, true, type, modelName, modelId, createdAt, now);
    }

    public DomainEvent copy() {
        return new DomainEvent(id, false, type, modelName, modelId, LocalDateTime.now(), null);
    }
}