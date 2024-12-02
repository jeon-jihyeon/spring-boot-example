package com.example.spring.boot.modules.team.api.data;

import com.example.spring.boot.modules.team.domain.repository.condition.TeamCondition;

import java.time.LocalDateTime;

public record TeamParam(String name, LocalDateTime startsAt) {
    public TeamCondition toCondition() {
        return new TeamCondition(name, startsAt);
    }
}
