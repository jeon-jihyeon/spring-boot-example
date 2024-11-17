package com.example.spring.boot.persistence.team;

import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TeamCommandAdapter implements TeamCommandRepository {
    private final TeamJpaRepository repository;
    private final TeamCommandMapper mapper;

    public TeamCommandAdapter(TeamJpaRepository repository, TeamCommandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public TeamId save(Team team) {
        return new TeamId(repository.save(mapper.toEntity(team)).getId());
    }

    @Override
    public Team findById(TeamId id) {
        return mapper.toDomain(repository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    @Transactional
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }
}
