package com.example.spring.boot.modules.team.domain.repository.query;

import com.example.spring.boot.modules.team.domain.model.Team;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.time.LocalDateTime;

public record TeamQuery(TeamId id, TeamName name, LocalDateTime startsAt) {
    public static TeamQuery from(Team domain) {
        return new TeamQuery(domain.getId(), domain.getName(), domain.getStartsAt());
    }
}
