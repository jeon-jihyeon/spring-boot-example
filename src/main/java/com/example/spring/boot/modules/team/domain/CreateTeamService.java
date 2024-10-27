package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.team.domain.command.CreateTeamCommand;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateTeamService {
    private final TeamCommandRepository repository;

    public CreateTeamService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void invoke(CreateTeamCommand command) {
        repository.save(Team.create(command));
    }
}
