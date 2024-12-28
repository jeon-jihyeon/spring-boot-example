package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventLayer;
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

    public static InboxEventEntity from(DomainEvent event) {
        return new InboxEventEntity(
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
