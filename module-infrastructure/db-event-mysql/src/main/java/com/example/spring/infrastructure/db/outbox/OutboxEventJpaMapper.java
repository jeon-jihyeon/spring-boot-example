package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutboxMapper;
import org.springframework.stereotype.Component;

@Component
public class OutboxEventJpaMapper implements DomainEventOutboxMapper<OutboxEventEntity> {
    @Override
    public DomainEvent toDomain(OutboxEventEntity entity) {
        return new DomainEvent(
                entity.getId(),
                entity.getType(),
                entity.getModelName(),
                entity.getModelId(),
                entity.getCompleted(),
                entity.getCompletedAt(),
                entity.getCreatedAt()
        );
    }

    @Override
    public OutboxEventEntity toEntity(DomainEvent event) {
        return new OutboxEventEntity(
                event.id(),
                event.type(),
                event.modelName(),
                event.modelId(),
                event.completed(),
                event.completedAt(),
                event.createdAt()
        );
    }
}
