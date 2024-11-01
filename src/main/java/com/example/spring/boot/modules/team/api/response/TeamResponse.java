package com.example.spring.boot.modules.team.api.response;

import com.example.spring.boot.modules.player.api.response.PlayerListResponse;
import com.example.spring.boot.modules.player.domain.query.PlayerListQuery;
import com.example.spring.boot.modules.team.domain.query.TeamQuery;

import java.time.LocalDateTime;
import java.util.List;

public record TeamResponse(Long id, String name, LocalDateTime startsAt, List<PlayerListResponse> players) {
    public static TeamResponse of(TeamQuery query, List<PlayerListQuery> players) {
        return new TeamResponse(
                query.id().value(),
                query.name().value(),
                query.startsAt(),
                players.stream().map(PlayerListResponse::from).toList()
        );
    }
}
