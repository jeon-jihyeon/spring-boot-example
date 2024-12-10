package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.team.domain.event.TeamCreateEvent;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;
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
