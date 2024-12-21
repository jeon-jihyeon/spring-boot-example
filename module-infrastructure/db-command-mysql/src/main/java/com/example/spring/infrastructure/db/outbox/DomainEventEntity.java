package com.example.spring.infrastructure.db.outbox;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "domain_events")
public class DomainEventEntity {
    @Id
    private Long id;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(length = 64, nullable = false)
    private String model;
    @Column(nullable = false)
    private Long modelId;
    @Column(nullable = false)
    private Boolean published;
    @Column
    private LocalDateTime publishedAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return Objects.equals(id, ((DomainEventEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("DomainEventEntity[id=%s, model=%s, modelId=%s, createdAt=%s, published=%s, publishedAt=%s]",
                id, model, modelId, createdAt, published, publishedAt);
    }
}
