package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventLayer;
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
            DomainEventLayer layer,
            DomainEventType type,
            String modelName,
            Long modelId,
            Boolean completed,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, layer, type, modelName, modelId, completed, completedAt, createdAt);
    }

    public static OutboxEventEntity from(DomainEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.layer(),
                event.type(),
                event.modelName(),
                event.modelId(),
                event.completed(),
                event.completedAt(),
                event.createdAt()
        );
    }
}
