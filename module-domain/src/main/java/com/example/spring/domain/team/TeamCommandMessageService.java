package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.domain.event.MessageProducer;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandMessageService {
    private final DomainEventOutbox outbox;
    private final MessageProducer producer;

    public TeamCommandMessageService(DomainEventOutbox outbox, MessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void send(DomainEventType type, TeamId teamId) {
        final DomainEvent event = DomainEvent.of(type, Team.class.getSimpleName().toLowerCase(), teamId.value());
        producer.send(event);
        outbox.save(event);
    }
}
