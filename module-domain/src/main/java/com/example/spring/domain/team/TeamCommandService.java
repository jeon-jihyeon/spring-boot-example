package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamCreateEvent;
import com.example.spring.domain.team.dto.TeamData;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class TeamCommandService {
    private final TeamCommandRepository repository;
    private final ApplicationEventPublisher publisher;

    public TeamCommandService(TeamCommandRepository repository, ApplicationEventPublisher publisher) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public TeamData read(TeamId id) {
        return repository.findById(id);
    }

    public TeamData create(TeamCreateCommand command) {
        final TeamData team = repository.save(TeamData.from(Team.create(command)));
        publisher.publishEvent(new TeamCreateEvent(team.id()));
        return team;
    }
}
