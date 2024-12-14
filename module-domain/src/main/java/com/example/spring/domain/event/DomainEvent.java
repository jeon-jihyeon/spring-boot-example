package com.example.spring.domain.event;

import java.time.LocalDateTime;

public record DomainEvent(
        Long id,
        String model,
        Long modelId,
        DomainEventType eventType,
        String data,
        LocalDateTime createdAt,
        Boolean published,
        LocalDateTime publishedAt
) {
}