package com.example.spring.domain.event;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DomainEventBatchService<E> {
    private final DomainEventOutboxMapper<E> mapper;
    private final DomainEventOutbox outbox;
    private final TeamCreateEventQueue queue;

    public DomainEventBatchService(DomainEventOutboxMapper<E> mapper, DomainEventOutbox outbox, TeamCreateEventQueue queue) {
        this.mapper = mapper;
        this.outbox = outbox;
        this.queue = queue;
    }

    @Transactional
    public void invoke(List<E> entities, LocalDateTime now) {
        final List<DomainEvent> events = entities.stream().map(e -> mapper.toDomain(e).complete(now)).toList();
        queue.pushAll(events);
        outbox.publishAll(events.stream().map(DomainEvent::id).toList(), now);
    }
}
