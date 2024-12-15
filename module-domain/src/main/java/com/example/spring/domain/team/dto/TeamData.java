package com.example.spring.domain.team.dto;

import com.example.spring.domain.team.Team;

import java.time.LocalDateTime;

public record TeamData(Long id, String name, LocalDateTime startsAt) {
    public static TeamData from(Team domain) {
        return new TeamData(domain.getId().value(), domain.getName().value(), domain.getStartsAt());
    }
}
