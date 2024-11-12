package com.example.spring.boot.persistence.team;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.persistence.EntityMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper implements EntityMapper<Team, TeamEntity> {
    @Override
    public Team toDomain(TeamEntity entity) {
        return Team.of(
                entity.getId(),
                entity.getName(),
                entity.getStartsAt()
        );
    }

    @Override
    public TeamEntity toEntity(Team domain) {
        return new TeamEntity(
                domain.getId().value(),
                domain.getName().value(),
                domain.getStartsAt()
        );
    }
}
