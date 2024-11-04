package com.example.spring.boot.modules.team.domain.repository.condition;

import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;

public record TeamCondition(TeamName name, LocalDateTime startsAt) {
}
