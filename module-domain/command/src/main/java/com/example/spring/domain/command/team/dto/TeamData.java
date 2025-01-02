package com.example.spring.domain.command.team.dto;

import com.example.spring.domain.command.team.model.Team;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.command.team.model.TeamName;

import java.time.LocalDateTime;

public record TeamData(TeamId id, TeamName name, LocalDateTime startsAt) {
    public static TeamData from(Team domain) {
        return new TeamData(domain.getId(), domain.getName(), domain.getStartsAt());
    }

    public static TeamData of(Long id, String name, LocalDateTime startsAt) {
        return new TeamData(new TeamId(id), new TeamName(name), startsAt);
    }
}
