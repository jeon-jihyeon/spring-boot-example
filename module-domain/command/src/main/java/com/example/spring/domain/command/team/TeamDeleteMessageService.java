package com.example.spring.domain.command.team;

import com.example.spring.domain.command.team.dto.TeamDeleteEvent;
import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.MessageProducer;
import org.springframework.stereotype.Component;

@Component
public class TeamDeleteMessageService {
    private final DomainEventOutbox outbox;
    private final MessageProducer producer;

    public TeamDeleteMessageService(DomainEventOutbox outbox, MessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void send(TeamDeleteEvent e) {
        final DomainEvent event = DomainEvent.deleteType("delete-team", e.teamId().value());
        producer.send(event);
        outbox.save(event);
    }
}
