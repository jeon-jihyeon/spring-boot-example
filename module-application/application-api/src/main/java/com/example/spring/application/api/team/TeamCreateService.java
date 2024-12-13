package com.example.spring.application.api.team;

import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.event.TeamCreateEvent;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import com.example.spring.domain.team.repository.TeamQueryRepository;
import com.example.spring.domain.team.repository.command.TeamCreateCommand;
import com.example.spring.domain.team.repository.query.TeamQuery;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreateService {
    private final TeamCommandRepository commandRepository;
    private final TeamQueryRepository queryRepository;
    private final ApplicationEventPublisher publisher;

    public TeamCreateService(
            TeamCommandRepository commandRepository,
            TeamQueryRepository queryRepository,
            ApplicationEventPublisher publisher
    ) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
        this.publisher = publisher;
    }

    @Transactional
    public TeamQuery invoke(TeamCreateCommand command) {
        final TeamId id = commandRepository.save(Team.create(command));
        publisher.publishEvent(new TeamCreateEvent(id.value(), command.playerIds()));
        return queryRepository.findTeam(id);
    }
}
