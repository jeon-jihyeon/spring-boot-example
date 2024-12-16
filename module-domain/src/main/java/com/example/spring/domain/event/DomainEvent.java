package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        String model,
        Long modelId,
        LocalDateTime createdAt,
        Boolean published,
        LocalDateTime publishedAt
) {
    public static DomainEvent of(String model, Long modelId) {
        return new DomainEvent(IdGenerator.newId(), model, modelId, LocalDateTime.now(), false, null);
    }

    public DomainEvent publish() {
        return new DomainEvent(id, model, modelId, createdAt, true, LocalDateTime.now());
    }
}