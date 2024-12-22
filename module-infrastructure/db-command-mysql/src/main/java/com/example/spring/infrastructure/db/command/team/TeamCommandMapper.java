package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.command.BaseCommandMapper;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class TeamCommandMapper implements BaseCommandMapper<TeamData, TeamEntity> {
    @Override
    public TeamData toDomain(TeamEntity entity) {
        return TeamData.of(
                entity.getId(),
                entity.getName(),
                entity.getStartsAt(),
                entity.getPlayerIds().stream().toList()
        );
    }

    @Override
    public TeamEntity toEntity(TeamData domain) {
        return new TeamEntity(
                domain.id().value(),
                domain.name().value(),
                domain.startsAt(),
                domain.playerIds().stream().map(PlayerId::value).collect(Collectors.toSet())
        );
    }
}
