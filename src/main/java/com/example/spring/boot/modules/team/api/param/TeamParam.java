package com.example.spring.boot.modules.team.api.param;

import com.example.spring.boot.modules.team.domain.condition.TeamCondition;
import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;

public record TeamParam(String name, LocalDateTime startsAt) {
    public TeamCondition toCondition() {
        return new TeamCondition(new TeamName(name), startsAt);
    }
}
