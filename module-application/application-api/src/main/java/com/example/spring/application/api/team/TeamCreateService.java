package com.example.spring.application.api.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventOutbox;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreateService {
    private final TeamCommandRepository repository;
    private final ApplicationEventPublisher publisher;
    private final DomainEventOutbox outbox;

    public TeamCreateService(TeamCommandRepository repository, ApplicationEventPublisher publisher, DomainEventOutbox outbox) {
        this.repository = repository;
        this.publisher = publisher;
        this.outbox = outbox;
    }

    @Transactional
    public TeamData invoke(TeamCreateCommand command) {
        final TeamData team = TeamData.from(repository.save(Team.create(command)));
        final DomainEvent event = DomainEvent.of(Team.class.getSimpleName(), team.id().value());
        publisher.publishEvent(event);
        outbox.save(event);
        return team;
    }
}
