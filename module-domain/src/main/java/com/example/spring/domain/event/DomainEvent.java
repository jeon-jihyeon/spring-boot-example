package com.example.spring.domain.event;

import com.example.spring.domain.IdGenerator;

import java.time.LocalDateTime;

public final class DomainEvent {
    private final Long id;
    private final String model;
    private final Long modelId;
    private final DomainEventType type;
    private final String data;
    private final LocalDateTime createdAt;
    private final Boolean published;
    private final LocalDateTime publishedAt;

    public DomainEvent(
            Long id,
            String model,
            Long modelId,
            DomainEventType type,
            String data,
            LocalDateTime createdAt,
            Boolean published,
            LocalDateTime publishedAt
    ) {
        this.id = id;
        this.model = model;
        this.modelId = modelId;
        this.type = type;
        this.data = data;
        this.createdAt = createdAt;
        this.published = published;
        this.publishedAt = publishedAt;
    }

    public static DomainEvent of(String model, Long modelId, DomainEventType type, String data) {
        LocalDateTime now = LocalDateTime.now();
        return new DomainEvent(IdGenerator.newId(), model, modelId, type, data, now, false, now);
    }

    public Long getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public Long getModelId() {
        return modelId;
    }

    public DomainEventType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getPublished() {
        return published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }
}