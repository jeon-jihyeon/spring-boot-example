package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.team.Team;
import com.example.spring.infrastructure.db.command.CommandMapper;
import org.springframework.stereotype.Component;

@Component
public class TeamCommandMapper implements CommandMapper<Team, TeamEntity> {
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
