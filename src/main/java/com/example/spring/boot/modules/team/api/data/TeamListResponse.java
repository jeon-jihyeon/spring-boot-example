package com.example.spring.boot.modules.team.api.data;

import com.example.spring.boot.modules.team.domain.repository.query.TeamListQuery;

import java.time.LocalDateTime;

public record TeamListResponse(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
    public static TeamListResponse from(TeamListQuery query) {
        return new TeamListResponse(query.id(), query.name(), query.startsAt(), query.playerCount());
    }
}
