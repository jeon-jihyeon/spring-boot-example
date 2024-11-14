package com.example.spring.boot.modules.team.domain.repository.query;

import com.example.spring.boot.modules.team.domain.model.Team;

import java.time.LocalDateTime;

public record TeamQuery(Long id, String name, LocalDateTime startsAt) {
    public static TeamQuery from(Team domain) {
        return new TeamQuery(domain.getId().value(), domain.getName().value(), domain.getStartsAt());
    }
}
