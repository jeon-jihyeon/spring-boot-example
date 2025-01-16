package com.example.spring.application.api.team.response;

import com.example.spring.application.api.player.response.PlayerQueryResponse;
import com.example.spring.domain.query.team.dto.TeamQuery;

import java.time.LocalDateTime;
import java.util.List;

public record TeamQueryResponse(Long id, String name, LocalDateTime startsAt, List<PlayerQueryResponse> players) {
    public static TeamQueryResponse from(TeamQuery query) {
        return new TeamQueryResponse(
                query.id(),
                query.name(),
                query.startsAt(),
                query.players().stream().map(PlayerQueryResponse::from).toList()
        );
    }
}
