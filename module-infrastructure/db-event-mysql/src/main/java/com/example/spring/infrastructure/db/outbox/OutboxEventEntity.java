package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
import com.example.spring.infrastructure.db.BaseEventEntity;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity(name = "outbox_events")
public class OutboxEventEntity extends BaseEventEntity {

    public OutboxEventEntity() {
    }

    public OutboxEventEntity(
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
