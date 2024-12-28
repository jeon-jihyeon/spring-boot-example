package com.example.spring.infrastructure.db.event.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventState;
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
            DomainEventState state,
            DomainEventType type,
            String modelName,
            Long modelId,
            LocalDateTime createdAt,
            LocalDateTime processedAt,
            LocalDateTime completedAt
    ) {
        super(id, state, type, modelName, modelId, createdAt, processedAt, completedAt);
    }

    public static OutboxEventEntity from(DomainEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.state(),
                event.type(),
                event.modelName(),
                event.modelId(),
                event.createdAt(),
                event.processedAt(),
                event.completedAt()
        );
    }
}
