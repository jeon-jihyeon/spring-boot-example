package com.example.spring.infrastructure.api;

import com.example.spring.domain.command.team.dto.TeamData;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.command.team.model.TeamName;

import java.time.LocalDateTime;

public record TeamApiResponse(Long id, String name, LocalDateTime startsAt) {
    public TeamData toData() {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt);
    }
}
