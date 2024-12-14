package com.example.spring.application.api.team;

import com.example.spring.domain.event.TeamCreateEvent;
import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.command.TeamCreateCommand;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreateService {
    private final TeamCommandRepository commandRepository;
    private final ApplicationEventPublisher publisher;

    public TeamCreateService(
            TeamCommandRepository commandRepository,
            ApplicationEventPublisher publisher
    ) {
        this.commandRepository = commandRepository;
        this.publisher = publisher;
    }

    @Transactional
    public TeamId invoke(TeamCreateCommand command) {
        final TeamId id = commandRepository.save(Team.create(command));
        publisher.publishEvent(new TeamCreateEvent(id.value(), command.playerIds()));
        return id;
    }
}
