package com.example.spring.boot.persistence.team.dto;

import com.example.spring.boot.modules.team.domain.repository.query.TeamListQuery;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record TeamListQueryDto(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
    @QueryProjection
    public TeamListQueryDto {
    }

    public TeamListQuery toQuery() {
        return new TeamListQuery(id, name, startsAt, playerCount);
    }
}
