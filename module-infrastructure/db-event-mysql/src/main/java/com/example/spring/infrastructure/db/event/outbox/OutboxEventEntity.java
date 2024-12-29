package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.event.BaseEventEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "outbox_events")
public class OutboxEventEntity extends BaseEventEntity {

    public OutboxEventEntity() {
    }

    private OutboxEventEntity(
            Long id,
            Boolean completed,
            DomainEventType type,
            String modelName,
            Long modelId,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
        super(id, completed, type, modelName, modelId, createdAt, completedAt);
    }

    public static OutboxEventEntity from(DomainEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.completed(),
                event.type(),
                event.modelName(),
                event.modelId(),
                event.createdAt(),
                event.completedAt()
        );
    }
}
