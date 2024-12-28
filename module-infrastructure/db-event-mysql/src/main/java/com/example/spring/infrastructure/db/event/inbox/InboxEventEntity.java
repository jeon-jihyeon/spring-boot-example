package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventState;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.event.BaseEventEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "inbox_events")
public class InboxEventEntity extends BaseEventEntity {
    public InboxEventEntity() {
    }

    private InboxEventEntity(
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

    public static InboxEventEntity from(DomainEvent event) {
        return new InboxEventEntity(
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
