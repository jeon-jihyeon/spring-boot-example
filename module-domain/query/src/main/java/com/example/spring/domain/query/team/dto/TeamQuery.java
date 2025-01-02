package com.example.spring.domain.query.team.dto;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.query.player.dto.PlayerQuery;

import java.time.LocalDateTime;
import java.util.List;

public record TeamQuery(Long id, String name, LocalDateTime startsAt, List<PlayerQuery> players) {
    public static TeamQuery from(TeamData data, List<PlayerData> players) {
        return new TeamQuery(
                data.id().value(),
                data.name().value(),
                data.startsAt(),
                players.stream().map(PlayerQuery::from).toList()
        );
    }
}
