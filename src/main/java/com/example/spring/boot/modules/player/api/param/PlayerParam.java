package com.example.spring.boot.modules.player.api.param;

import com.example.spring.boot.modules.player.domain.condition.PlayerCondition;
import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.team.domain.model.TeamId;

public record PlayerParam(Grade grade, String firstName, String lastName, Long teamId) {
    public PlayerCondition toCondition() {
        return new PlayerCondition(grade, new FullName(firstName, lastName), new TeamId(teamId));
    }
}
