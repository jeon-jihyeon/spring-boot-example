package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.model.DomainEvent;
import com.example.spring.domain.event.model.DomainEventType;
import com.example.spring.infrastructure.db.command.BaseCommandEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDateTime;

@Entity(name = "outbox_events")
public class OutboxEventEntity extends BaseCommandEntity {
    @Column(nullable = false)
    private Boolean completed;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private DomainEventType type;
    @Column(length = 64, nullable = false)
    private String queueName;
    @Column(nullable = false)
    private Long modelId;
    @Column
    private LocalDateTime completedAt;

    public OutboxEventEntity(
            Long id,
            Boolean completed,
            DomainEventType type,
            String queueName,
            Long modelId,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, createdAt);
        this.completed = completed;
        this.type = type;
        this.queueName = queueName;
        this.modelId = modelId;
        this.completedAt = completedAt;
    }

    public OutboxEventEntity() {
    }

    public static OutboxEventEntity from(DomainEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.completed(),
                event.type(),
                event.queueName(),
                event.modelId(),
                event.completedAt(),
                event.createdAt()
        );
    }

    public DomainEvent toModel() {
        return new DomainEvent(getId(), completed, type, queueName, modelId, getCreatedAt(), completedAt);
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

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String model) {
        this.queueName = model;
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
}
