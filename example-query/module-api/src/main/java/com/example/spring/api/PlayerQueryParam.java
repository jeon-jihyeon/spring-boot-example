package com.example.spring.api;

import com.example.spring.domain.query.PlayerQueryCondition;
import jakarta.validation.constraints.NotNull;

public record PlayerQueryParam(@NotNull Long teamId) {
    public PlayerQueryCondition toCondition() {
        return new PlayerQueryCondition(teamId);
    }
}
