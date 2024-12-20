package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.Team;
import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.TeamId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
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
    public Team save(Team team) {
        return mapper.toDomain(repository.save(mapper.toEntity(team)));
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
