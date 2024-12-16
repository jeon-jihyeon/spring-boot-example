package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.Team;
import com.example.spring.infrastructure.db.command.base.BaseCommandMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamCommandMapper implements BaseCommandMapper<Team, TeamEntity> {
    @Override
    public Team toDomain(TeamEntity entity) {
        return Team.of(
                entity.getId(),
                entity.getName(),
                entity.getStartsAt(),
                entity.getPlayerIds().stream().map(PlayerId::new).toList()
        );
    }

    @Override
    public TeamEntity toEntity(Team domain) {
        return new TeamEntity(
                domain.getId().value(),
                domain.getName().value(),
                domain.getStartsAt(),
                domain.getPlayerIds().stream().map(PlayerId::value).collect(Collectors.toSet())
        );
    }
}
