package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamLeavePlayerCommand;
import org.springframework.stereotype.Service;

@Service
public class TeamCommandLeavePlayerService {
    private final TeamCommandRepository repository;

    public TeamCommandLeavePlayerService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    public TeamData invoke(TeamLeavePlayerCommand command) {
        return repository.save(TeamData.from(repository.findById(command.teamId()).toModel().removePlayer(command.playerId())));
    }
}
