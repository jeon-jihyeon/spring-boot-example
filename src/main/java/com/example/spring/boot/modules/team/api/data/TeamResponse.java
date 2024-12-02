package com.example.spring.boot.modules.team.api.data;

import com.example.spring.boot.modules.team.domain.repository.query.TeamQuery;

import java.time.LocalDateTime;

public record TeamResponse(Long id, String name, LocalDateTime startsAt) {
    public static TeamResponse from(TeamQuery query) {
        return new TeamResponse(query.id(), query.name(), query.startsAt());
    }
}
