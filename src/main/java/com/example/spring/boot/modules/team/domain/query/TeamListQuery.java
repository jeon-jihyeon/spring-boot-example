package com.example.spring.boot.modules.team.domain.query;

import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;

public record TeamListQuery(TeamId id, TeamName name, LocalDateTime startsAt, Integer playerCount) {
}
