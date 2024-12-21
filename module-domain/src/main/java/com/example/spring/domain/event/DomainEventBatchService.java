package com.example.spring.domain.event;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DomainEventBatchService<E> {
    private final DomainEventMapper<E> mapper;
    private final DomainEventOutbox outbox;
    private final DomainEventQueue queue;

    public DomainEventBatchService(DomainEventMapper<E> mapper, DomainEventOutbox outbox, DomainEventQueue queue) {
        this.mapper = mapper;
        this.outbox = outbox;
        this.queue = queue;
    }

    @Transactional
    public void invoke(List<E> entities, LocalDateTime now) {
        final List<DomainEvent> events = entities.stream().map(e -> mapper.toDomain(e).publish(now)).toList();
        queue.pushAll(events);
        outbox.publishAll(events.stream().map(DomainEvent::id).toList(), now);
    }
}
