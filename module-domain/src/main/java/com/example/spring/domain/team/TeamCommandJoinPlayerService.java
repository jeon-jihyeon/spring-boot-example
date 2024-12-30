package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamJoinPlayerCommand;
import org.springframework.stereotype.Service;

@Service
public class TeamCommandJoinPlayerService {
    private final TeamCommandRepository repository;

    public TeamCommandJoinPlayerService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    public TeamData invoke(TeamJoinPlayerCommand command) {
        return repository.save(TeamData.from(repository.findById(command.teamId()).toModel().addPlayer(command.playerId())));
    }
}
