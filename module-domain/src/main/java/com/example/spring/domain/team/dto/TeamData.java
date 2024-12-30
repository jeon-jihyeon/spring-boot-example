package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.Team;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.domain.team.model.TeamName;

import java.time.LocalDateTime;
import java.util.List;

public record TeamData(TeamId id, TeamName name, LocalDateTime startsAt, List<PlayerId> playerIds) {
    public static TeamData from(Team domain) {
        return new TeamData(domain.getId(), domain.getName(), domain.getStartsAt(), domain.getPlayerIds());
    }

    public static TeamData of(Long id, String name, LocalDateTime startsAt, List<Long> playerIds) {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt, playerIds.stream().map(PlayerId::new).toList());
    }

    public Team toModel() {
        return Team.of(id, name, startsAt, playerIds);
    }
}
