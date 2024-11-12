package com.example.spring.boot.persistence.team;

import com.example.spring.boot.core.exception.EntityNotFoundException;
import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.repository.TeamCommandRepository;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandAdapter implements TeamCommandRepository {
    private final TeamJpaRepository jpaRepository;
    private final TeamMapper mapper;

    public TeamCommandAdapter(TeamJpaRepository jpaRepository, TeamMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public TeamId save(Team team) {
        return new TeamId(jpaRepository.save(mapper.toEntity(team)).getId());
    }

    @Override
    public Team findById(TeamId id) {
        return mapper.toDomain(jpaRepository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public void deleteById(TeamId id) {
        jpaRepository.deleteById(id.value());
    }
}
