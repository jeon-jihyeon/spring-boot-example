package com.example.spring.infrastructure.db.command.outbox;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.command.base.BaseCommandMapper;
import org.springframework.stereotype.Component;

@Component
public class DomainEventMapper implements BaseCommandMapper<DomainEvent, DomainEventEntity> {
    @Override
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

    @Override
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
