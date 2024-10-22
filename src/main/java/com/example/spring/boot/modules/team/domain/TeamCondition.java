package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;

public record TeamCondition(
        TeamName name,
        LocalDateTime startsAt
) {
}
