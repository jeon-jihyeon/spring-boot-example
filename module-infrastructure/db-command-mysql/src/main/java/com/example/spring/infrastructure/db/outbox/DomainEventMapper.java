package com.example.spring.infrastructure.db.outbox;

import com.example.spring.domain.event.DomainEvent;
import org.springframework.stereotype.Component;

@Component
public class DomainEventMapper {
    public DomainEvent toDomain(DomainEventEntity entity) {
        return new DomainEvent(
                entity.getId(),
                entity.getModel(),
                entity.getModelId(),
                entity.getCreatedAt(),
                entity.getPublished(),
                entity.getPublishedAt()
        );
    }

    public DomainEventEntity toEntity(DomainEvent event) {
        return new DomainEventEntity(
                event.id(),
                event.model(),
                event.modelId(),
                event.createdAt(),
                event.published(),
                event.publishedAt()
        );
    }
}
