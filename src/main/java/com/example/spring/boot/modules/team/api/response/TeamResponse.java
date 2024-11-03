package com.example.spring.boot.modules.team.api.response;

import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.team.domain.query.TeamQuery;

import java.time.LocalDateTime;
import java.util.List;

public record TeamResponse(Long id, String name, LocalDateTime startsAt, List<PlayerId> playerIds) {
    public static TeamResponse from(TeamQuery query) {
        return new TeamResponse(query.id().value(), query.name().value(), query.startsAt(), query.playerIds());
    }
}
