package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamDeleteEvent;
import org.springframework.stereotype.Component;

@Component
public class TeamMessageService {
    private final DomainEventOutbox outbox;
    private final TeamMessageProducer producer;

    public TeamMessageService(DomainEventOutbox outbox, TeamMessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    public void sendCreateType(TeamCreateEvent event) {
        final DomainEvent domainEvent = event.generalize();
        producer.send(domainEvent);
        outbox.save(domainEvent);
    }

    public void sendDeleteType(TeamDeleteEvent event) {
        final DomainEvent domainEvent = event.generalize();
        producer.send(domainEvent);
        outbox.save(domainEvent);
    }
}
