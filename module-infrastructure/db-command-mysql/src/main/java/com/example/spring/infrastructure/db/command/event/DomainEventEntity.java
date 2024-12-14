package com.example.spring.infrastructure.db.command.event;

import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.command.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Entity(name = "domain_events")
public class DomainEventEntity extends BaseCommandEntity {
    @Column(length = 64, nullable = false)
    private String model;
    @Column(nullable = false)
    private Long modelId;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 6)
    private DomainEventType type;
    @Column(length = 512)
    private String data;
    @Column(nullable = false)
    private Boolean published;
    @Column(nullable = false)
    private LocalDateTime publishedAt;

    public DomainEventEntity(
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

    public DomainEventEntity() {
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

    public Boolean getPublished() {
        return published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

}
