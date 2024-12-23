package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandAdapter implements TeamCommandRepository {
    private final TeamJpaRepository repository;

    public TeamCommandAdapter(TeamJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public TeamData save(TeamData team) {
        return repository.save(TeamEntity.from(team)).toData();
    }

    @Override
    public TeamData findById(TeamId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }
}
