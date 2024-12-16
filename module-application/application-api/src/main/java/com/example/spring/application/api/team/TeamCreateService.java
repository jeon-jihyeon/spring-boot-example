package com.example.spring.application.api.team;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreateService {
    private final TeamCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public TeamCreateService(TeamCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    @Transactional
    public TeamData invoke(TeamCreateCommand command) {
        final TeamData team = TeamData.from(repository.save(Team.create(command)));
        publisher.publishEvent(DomainEvent.of(Team.class.getSimpleName(), team.id()));
        return team;
    }
}
