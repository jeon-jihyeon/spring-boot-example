package com.example.spring.boot.modules.player.api.param;

import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.repository.condition.PlayerCondition;

public record PlayerParam(Grade grade, String firstName, String lastName, Long teamId) {
    public PlayerCondition toCondition() {
        return new PlayerCondition(grade, firstName, lastName, teamId);
    }
}
