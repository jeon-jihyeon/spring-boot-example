package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.repository.TeamCommandRepository;
import com.example.spring.infrastructure.db.command.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandAdapter implements TeamCommandRepository {
    private final TeamJpaRepository repository;
    private final TeamCommandMapper mapper;

    public TeamCommandAdapter(TeamJpaRepository repository, TeamCommandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TeamId save(Team team) {
        return new TeamId(repository.save(mapper.toEntity(team)).getId());
    }

    @Override
    public Team findById(TeamId id) {
        return mapper.toDomain(repository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }
}