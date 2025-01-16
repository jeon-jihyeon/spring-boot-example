package com.example.spring.application.api.team.request;

import com.example.spring.domain.query.team.dto.TeamQueryCondition;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TeamQueryParam(@NotNull LocalDateTime startsAt) {
    public TeamQueryCondition toCondition() {
        return new TeamQueryCondition(startsAt);
    }
}
