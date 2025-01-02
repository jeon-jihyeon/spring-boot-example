package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.dto.PlayerQuery;

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
