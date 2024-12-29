package com.example.spring.domain.event;

import com.example.spring.domain.event.dto.DomainEventCommand;
import org.springframework.stereotype.Component;

@Component
public class CommandMessageService {
    private final DomainEventOutbox outbox;
    private final CommandMessageProducer producer;

    public CommandMessageService(DomainEventOutbox outbox, CommandMessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void sendCreateType(DomainEventCommand command) {
        final DomainEvent event = DomainEvent.createType(command);
        producer.send(event);
        outbox.save(event);
    }

    public void sendUpdateType(DomainEventCommand command) {
        final DomainEvent event = DomainEvent.updateType(command);
        producer.send(event);
        outbox.save(event);
    }

    public void sendDeleteType(DomainEventCommand command) {
        final DomainEvent event = DomainEvent.deleteType(command);
        producer.send(event);
        outbox.save(event);
    }
}
