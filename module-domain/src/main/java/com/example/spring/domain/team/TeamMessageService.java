package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Component;

@Component
public class TeamMessageService {
    private final DomainEventOutbox outbox;
    private final TeamMessageProducer producer;

    public TeamMessageService(DomainEventOutbox outbox, TeamMessageProducer producer) {
        this.outbox = outbox;
        this.producer = producer;
    }

    private String getModelName() {
        return Team.class.getSimpleName();
    }

    public void sendCreateType(TeamId teamId) {
        final DomainEvent event = DomainEvent.createType(getModelName(), teamId.value());
        producer.send(event);
        outbox.save(event);
    }

    public void sendUpdateType(TeamId teamId) {
        final DomainEvent event = DomainEvent.updateType(getModelName(), teamId.value());
        producer.send(event);
        outbox.save(event);
    }

    public void sendDeleteType(TeamId teamId) {
        final DomainEvent event = DomainEvent.createType(getModelName(), teamId.value());
        producer.send(event);
        outbox.save(event);
    }
}
