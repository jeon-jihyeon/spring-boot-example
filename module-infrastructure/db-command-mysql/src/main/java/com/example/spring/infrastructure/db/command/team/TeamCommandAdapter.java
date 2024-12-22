package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.TeamId;
import com.example.spring.domain.team.dto.TeamData;
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
    public TeamData save(TeamData team) {
        return mapper.toDomain(repository.save(mapper.toEntity(team)));
    }

    @Override
    public TeamData findById(TeamId id) {
        return mapper.toDomain(repository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }
}
