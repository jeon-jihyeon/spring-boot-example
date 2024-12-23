package com.example.spring.domain.event;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class DomainEventBatchService {
    private final DomainEventOutbox outbox;
    private final DomainEventProducer producer;

    public DomainEventBatchService(DomainEventOutbox outbox, DomainEventProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    @Transactional
    public void invoke(List<DomainEvent> events, LocalDateTime now) throws Exception {
        producer.sendBatch(events);
        outbox.publishAll(events.stream().map(DomainEvent::id).toList(), now);
    }
}
