package com.example.spring.infrastructure.db;

import com.example.spring.domain.event.DomainEvent;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class BaseEventEntity {
    @Id
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DomainEvent.Type type;
    @Column(length = 64, nullable = false)
    private String modelName;
    @Column(nullable = false)
    private Long modelId;
    @Column(nullable = false)
    private Boolean completed;
    @Column
    private LocalDateTime completedAt;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BaseEventEntity(
            Long id,
            DomainEvent.Type type,
            String modelName,
            Long modelId,
            Boolean completed,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.type = type;
        this.modelName = modelName;
        this.modelId = modelId;
        this.completed = completed;
        this.completedAt = completedAt;
        this.createdAt = createdAt;
    }

    public BaseEventEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DomainEvent.Type getType() {
        return type;
    }

    public void setType(DomainEvent.Type type) {
        this.type = type;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String model) {
        this.modelName = model;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean published) {
        this.completed = published;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime publishedAt) {
        this.completedAt = publishedAt;
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
        return Objects.equals(id, ((BaseEventEntity) o).getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("DomainEventEntity[id=%s, type=%s, modelName=%s, modelId=%s, createdAt=%s, completed=%s, completedAt=%s]",
                id, type, modelName, modelId, createdAt, completed, completedAt);
    }
}
