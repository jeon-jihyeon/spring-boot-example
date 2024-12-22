package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        String modelName,
        Long modelId,
        LocalDateTime createdAt,
        Boolean published,
        LocalDateTime publishedAt
) {
    public static DomainEvent of(String model, Long modelId) {
        return new DomainEvent(IdGenerator.newId(), model, modelId, LocalDateTime.now(), false, null);
    }

    public DomainEvent publish(LocalDateTime now) {
        return new DomainEvent(id, modelName, modelId, createdAt, true, now);
    }

    public DomainEvent publish() {
        return publish(LocalDateTime.now());
    }
}