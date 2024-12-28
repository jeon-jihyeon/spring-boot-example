package com.example.spring.infrastructure.db.event;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventState;
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
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DomainEventState state;
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
    private LocalDateTime processedAt;
    @Column
    private LocalDateTime completedAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public BaseEventEntity(
            Long id,
            DomainEventState state,
            DomainEventType type,
            String modelName,
            Long modelId,
            LocalDateTime createdAt,
            LocalDateTime processedAt,
            LocalDateTime completedAt
    ) {
        this.id = id;
        this.state = state;
        this.type = type;
        this.modelName = modelName;
        this.modelId = modelId;
        this.createdAt = createdAt;
        this.processedAt = processedAt;
        this.completedAt = completedAt;
    }

    public BaseEventEntity() {
    }

    public DomainEvent toModel() {
        return new DomainEvent(id, state, type, modelName, modelId, createdAt, processedAt, completedAt);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DomainEventState getState() {
        return state;
    }

    public void setState(DomainEventState layer) {
        this.state = layer;
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

    public LocalDateTime getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(LocalDateTime processedAt) {
        this.processedAt = processedAt;
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
        return String.format("DomainEventEntity[teamId=%s, state=%s, type=%s, modelName=%s, modelId=%s, createdAt=%s, processedAt=%s, completedAt=%s]",
                id, state, type, modelName, modelId, createdAt, processedAt, completedAt);
    }
}
