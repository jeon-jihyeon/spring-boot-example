package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;
import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamCreateService {
    private final TeamCommandRepository commandRepository;
    private final TeamQueryRepository queryRepository;

    public TeamCreateService(
            TeamCommandRepository commandRepository,
            TeamQueryRepository queryRepository
    ) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Transactional
    public TeamQuery invoke(TeamCreateCommand command) {
        final TeamId id = commandRepository.save(Team.create(command));
        return queryRepository.findTeam(id);
    }
}
