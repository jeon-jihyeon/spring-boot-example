package com.example.spring.infrastructure.db.command.event;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.command.CommandMapper;
import org.springframework.stereotype.Component;

@Component
public class DomainEventMapper implements CommandMapper<DomainEvent, DomainEventEntity> {
    @Override
    public DomainEvent toDomain(DomainEventEntity entity) {
        return new DomainEvent(
                entity.getId(),
                entity.getModel(),
                entity.getModelId(),
                entity.getType(),
                entity.getData(),
                entity.getCreatedAt(),
                entity.getPublished(),
                entity.getPublishedAt()
        );
    }

    @Override
    public DomainEventEntity toEntity(DomainEvent event) {
        return new DomainEventEntity(
                event.getId(),
                event.getModel(),
                event.getModelId(),
                event.getType(),
                event.getData(),
                event.getCreatedAt(),
                event.getPublished(),
                event.getPublishedAt()
        );
    }
}
