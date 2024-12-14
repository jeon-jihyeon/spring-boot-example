package com.example.spring.domain.team.query;

import com.example.spring.domain.team.Team;

import java.time.LocalDateTime;

public record TeamQuery(Long id, String name, LocalDateTime startsAt) {
    public static TeamQuery from(Team domain) {
        return new TeamQuery(domain.getId().value(), domain.getName().value(), domain.getStartsAt());
    }
}
