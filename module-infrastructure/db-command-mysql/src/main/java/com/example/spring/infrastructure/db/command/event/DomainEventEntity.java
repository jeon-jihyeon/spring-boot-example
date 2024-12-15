package com.example.spring.infrastructure.db.command.event;

import com.example.spring.infrastructure.db.command.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "domain_events")
public class DomainEventEntity extends BaseCommandEntity {
    @Column(length = 64, nullable = false)
    private String model;
    @Column(nullable = false)
    private Long modelId;
    @Column(nullable = false)
    private Boolean published;
    @Column(nullable = false)
    private LocalDateTime publishedAt;

    public DomainEventEntity(
            Long id,
            String model,
            Long modelId,
            LocalDateTime createdAt,
            Boolean published,
            LocalDateTime publishedAt
    ) {
        this.id = id;
        this.model = model;
        this.modelId = modelId;
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

    public Boolean getPublished() {
        return published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

}
