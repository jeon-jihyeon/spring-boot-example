package com.example.spring.domain.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.event.Layer;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import jakarta.transaction.Transactional;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TeamCommandService {
    private final TeamCommandRepository repository;
    private final ApplicationEventPublisher publisher;
    private final DomainEventOutbox outbox;

    public TeamCommandService(
            TeamCommandRepository repository,
            ApplicationEventPublisher publisher,
            DomainEventOutbox outbox
    ) {
        this.repository = repository;
        this.publisher = publisher;
        this.outbox = outbox;
    }

    public TeamData read(TeamId id) {
        return repository.findById(id);
    }

    @Transactional
    public TeamData create(TeamCreateCommand command) {
        final TeamData team = repository.save(TeamData.from(Team.create(command)));
        final DomainEvent event = DomainEvent.createType(Layer.DOMAIN, Team.class.getSimpleName(), team.id().value());
        publisher.publishEvent(event);
        outbox.save(event);
        return team;
    }
}
