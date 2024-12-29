package com.example.spring.infrastructure.db.event;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Objects;

@MappedSuperclass
public class BaseEventEntity {
    @Id
    private Long id;
    @Column(nullable = false)
    private Boolean completed;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DomainEventType type;
    @Column(length = 64, nullable = false)
    private String modelName;
    @Column(nullable = false)
    private Long modelId;
    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column
    private LocalDateTime completedAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BaseEventEntity(
            Long id,
            Boolean completed,
            DomainEventType type,
            String modelName,
            Long modelId,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
        this.id = id;
        this.completed = completed;
        this.type = type;
        this.modelName = modelName;
        this.modelId = modelId;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    public BaseEventEntity() {
    }

    public DomainEvent toModel() {
        return new DomainEvent(id, completed, type, modelName, modelId, createdAt, completedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public DomainEventType getType() {
        return type;
    }

    public void setType(DomainEventType type) {
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
        return String.format("DomainEventEntity[teamId=%s, completed=%s, type=%s, modelName=%s, modelId=%s, createdAt=%s, completedAt=%s]",
                id, completed, type, modelName, modelId, createdAt, completedAt);
    }
}
