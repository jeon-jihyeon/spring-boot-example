package com.example.spring.boot.modules.team.api.response;

import com.example.spring.boot.modules.team.domain.query.TeamListQuery;

import java.time.LocalDateTime;

public record TeamListResponse(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
    public static TeamListResponse from(TeamListQuery query) {
        return new TeamListResponse(query.id().value(), query.name().value(), query.startsAt(), query.playerCount());
    }
}
