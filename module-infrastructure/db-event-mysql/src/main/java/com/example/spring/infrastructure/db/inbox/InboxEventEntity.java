package com.example.spring.infrastructure.db.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
import com.example.spring.infrastructure.db.BaseEventEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "inbox_events")
public class InboxEventEntity extends BaseEventEntity {
    public InboxEventEntity() {
    }

    public InboxEventEntity(
            Long id,
            Layer layer,
            DomainEvent.Type type,
            String modelName,
            Long modelId,
            Boolean completed,
            LocalDateTime completedAt,
            LocalDateTime createdAt
    ) {
        super(id, layer, type, modelName, modelId, completed, completedAt, createdAt);
    }
}
