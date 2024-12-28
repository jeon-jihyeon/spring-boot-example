package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;
import com.example.spring.domain.event.dto.DomainEventCommand;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        DomainEventState state,
        DomainEventType type,
        String modelName,
        Long modelId,
        LocalDateTime createdAt,
        LocalDateTime processedAt,
        LocalDateTime completedAt
) {
    public DomainEvent {
        if (StringUtils.hasText(modelName)) modelName = modelName.toLowerCase();
    }

    private static DomainEvent of(DomainEventType type, String modelName, Long modelId) {
        return new DomainEvent(
                IdGenerator.newId(),
                DomainEventState.CREATED,
                type,
                modelName,
                modelId,
                LocalDateTime.now(),
                null,
                null
        );
    }

    public static DomainEvent createType(DomainEventCommand command) {
        return DomainEvent.of(DomainEventType.CREATE, command.modelName(), command.modelId());
    }

    public static DomainEvent updateType(DomainEventCommand command) {
        return DomainEvent.of(DomainEventType.UPDATE, command.modelName(), command.modelId());
    }

    public DomainEvent complete() {
        return complete(LocalDateTime.now());
    }

    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, DomainEventState.COMPLETED, type, modelName, modelId, createdAt, processedAt, now);
    }

    public DomainEvent process() {
        return process(LocalDateTime.now());
    }

    public DomainEvent process(LocalDateTime now) {
        return new DomainEvent(id, DomainEventState.PROCESSED, type, modelName, modelId, createdAt, now, completedAt);
    }

    public DomainEvent copy() {
        return new DomainEvent(id, DomainEventState.CREATED, type, modelName, modelId, createdAt, null, null);
    }

    public Boolean isProcessed() {
        return state.equals(DomainEventState.PROCESSED);
    }
}