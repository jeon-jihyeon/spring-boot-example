package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.repository.condition.PlayerCondition;

public record PlayerParam(Grade grade, String firstName, String lastName, Long teamId) {
    public PlayerCondition toCondition() {
        return new PlayerCondition(grade, firstName, lastName, teamId);
    }
}
