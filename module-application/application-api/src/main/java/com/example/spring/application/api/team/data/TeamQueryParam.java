package com.example.spring.application.api.team.data;

import com.example.spring.domain.team.dto.TeamQueryCondition;

import java.time.LocalDateTime;

public record TeamQueryParam(LocalDateTime startsAt) {
    public TeamQueryParam {
        if (startsAt == null) startsAt = LocalDateTime.now();
    }

    public TeamQueryCondition toCondition() {
        return new TeamQueryCondition(startsAt);
    }
}
