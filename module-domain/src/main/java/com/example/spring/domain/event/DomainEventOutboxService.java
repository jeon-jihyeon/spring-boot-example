package com.example.spring.domain.event;

import com.example.spring.domain.event.dto.DomainEventCommand;
import org.springframework.stereotype.Component;

@Component
public class DomainEventOutboxService {
    private final DomainEventOutbox outbox;
    private final DomainEventProducer producer;

    public DomainEventOutboxService(DomainEventOutbox outbox, DomainEventProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void create(DomainEventCommand command) {
        outbox.save(DomainEvent.createType(command));
    }

    public void process(DomainEventCommand command) {
        outbox.save(outbox.findEvent(command).process());
    }

    public void complete(DomainEvent event) {
        outbox.save(event.complete());
        producer.send(event);
    }
}
