package com.example.spring.application.api.team.data;

import com.example.spring.domain.team.repository.condition.TeamCondition;

import java.time.LocalDateTime;

public record TeamParam(String name, LocalDateTime startsAt) {
    public TeamCondition toCondition() {
        return new TeamCondition(name, startsAt);
    }
}
