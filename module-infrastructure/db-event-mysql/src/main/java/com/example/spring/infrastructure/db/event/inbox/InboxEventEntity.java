package com.example.spring.infrastructure.db.event.inbox;

import com.example.spring.domain.event.DomainEvent;
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
            Boolean completed,
            DomainEventType type,
            String queueName,
            Long modelId,
            LocalDateTime createdAt,
            LocalDateTime completedAt
    ) {
        super(id, completed, type, queueName, modelId, createdAt, completedAt);
    }

    public static InboxEventEntity from(DomainEvent event) {
        return new InboxEventEntity(
                event.id(),
                event.completed(),
                event.type(),
                event.queueName(),
                event.modelId(),
                event.createdAt(),
                event.completedAt()
        );
    }
}
