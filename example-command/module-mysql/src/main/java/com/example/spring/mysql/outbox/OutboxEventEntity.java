package com.example.spring.mysql.outbox;

import com.example.spring.domain.outbox.OutboxEvent;
import com.example.spring.domain.outbox.OutboxEventType;
import com.example.spring.mysql.BaseCommandEntity;
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
    @Column(nullable = false, length = 16)
    private OutboxEventType type;
    @Column(nullable = false)
    private Long entityId;
    @Column
    private LocalDateTime completedAt;

    public OutboxEventEntity(
            Long id,
            Boolean completed,
            OutboxEventType type,
            Long entityId,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, createdAt);
        this.completed = completed;
        this.type = type;
        this.entityId = entityId;
        this.completedAt = completedAt;
    }

    public OutboxEventEntity() {
    }

    public static OutboxEventEntity from(OutboxEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.completed(),
                event.type(),
                event.entityId(),
                event.completedAt(),
                event.createdAt()
        );
    }

    public OutboxEvent toModel() {
        return new OutboxEvent(getId(), completed, type, entityId, getCreatedAt(), completedAt);
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public OutboxEventType getType() {
        return type;
    }

    public void setType(OutboxEventType type) {
        this.type = type;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long modelId) {
        this.entityId = modelId;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime publishedAt) {
        this.completedAt = publishedAt;
    }
}
