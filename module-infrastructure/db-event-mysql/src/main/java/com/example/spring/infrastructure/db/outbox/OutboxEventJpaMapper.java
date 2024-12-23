package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventJpaMapper {
    public DomainEvent toDomain(OutboxEventEntity entity) {
        return new DomainEvent(
                entity.getId(),
                entity.getLayer(),
                entity.getType(),
                entity.getModelName(),
                entity.getModelId(),
                entity.getCompleted(),
                entity.getCompletedAt(),
                entity.getCreatedAt()
        );
    }

    public OutboxEventEntity toEntity(DomainEvent event) {
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
