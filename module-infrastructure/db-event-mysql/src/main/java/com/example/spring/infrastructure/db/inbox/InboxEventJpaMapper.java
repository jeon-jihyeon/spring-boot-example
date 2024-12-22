package com.example.spring.infrastructure.db.inbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInboxMapper;
import org.springframework.stereotype.Component;

@Component
public class InboxEventJpaMapper implements DomainEventInboxMapper<InboxEventEntity> {
    @Override
    public DomainEvent toDomain(InboxEventEntity entity) {
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
    public InboxEventEntity toEntity(DomainEvent event) {
        return new InboxEventEntity(
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
