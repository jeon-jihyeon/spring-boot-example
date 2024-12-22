package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        Type type,
        String modelName,
        Long modelId,
        Boolean completed,
        LocalDateTime completedAt,
        LocalDateTime createdAt
) {
    public DomainEvent {
        if (StringUtils.hasText(modelName)) modelName = modelName.toLowerCase();
    }

    private static DomainEvent of(Type type, String modelName, Long modelId) {
        return new DomainEvent(IdGenerator.newId(), type, modelName, modelId, false, null, LocalDateTime.now());
    }

    public static DomainEvent createType(String modelName, Long modelId) {
        return DomainEvent.of(Type.CREATE, modelName, modelId);
    }

    public static DomainEvent commandType(String modelName, Long modelId) {
        return DomainEvent.of(Type.COMMAND, modelName, modelId);
    }

    public DomainEvent complete(LocalDateTime now) {
        return new DomainEvent(id, type, modelName, modelId, true, now, createdAt);
    }

    public DomainEvent complete() {
        return complete(LocalDateTime.now());
    }

    public enum Type {CREATE, COMMAND}
}