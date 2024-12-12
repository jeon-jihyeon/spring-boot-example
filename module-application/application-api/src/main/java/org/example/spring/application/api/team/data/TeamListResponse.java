package org.example.spring.application.api.team.data;

import com.example.spring.domain.team.repository.query.TeamListQuery;

import java.time.LocalDateTime;

public record TeamListResponse(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
    public static TeamListResponse from(TeamListQuery query) {
        return new TeamListResponse(query.id(), query.name(), query.startsAt(), query.playerCount());
    }
}
