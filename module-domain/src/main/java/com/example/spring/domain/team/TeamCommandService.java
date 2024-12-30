package com.example.spring.domain.team;

import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.dto.TeamDeleteCommand;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.stereotype.Service;

@Service
public class TeamCommandService {
    private final TeamCommandRepository repository;

    public TeamCommandService(TeamCommandRepository repository) {
        this.repository = repository;
    }

    public TeamData read(TeamId id) {
        return repository.findById(id);
    }

    public TeamData create(TeamCreateCommand command) {
        return repository.save(TeamData.from(Team.create(command)));
    }

    public void delete(TeamDeleteCommand command) {
        repository.deleteById(command.id());
    }
}
