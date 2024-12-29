package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;
import com.example.spring.domain.event.dto.DomainEventCommand;
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

    public static DomainEvent createType(DomainEventCommand command) {
        return DomainEvent.of(DomainEventType.CREATE, command.modelName(), command.modelId());
    }

    public static DomainEvent updateType(DomainEventCommand command) {
        return DomainEvent.of(DomainEventType.UPDATE, command.modelName(), command.modelId());
    }

    public static DomainEvent deleteType(DomainEventCommand command) {
        return DomainEvent.of(DomainEventType.DELETE, command.modelName(), command.modelId());
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