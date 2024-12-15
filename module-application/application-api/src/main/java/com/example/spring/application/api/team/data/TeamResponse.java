package com.example.spring.application.api.team.data;

import com.example.spring.domain.team.dto.TeamData;

import java.time.LocalDateTime;

public record TeamResponse(Long id, String name, LocalDateTime startsAt) {
    public static TeamResponse from(TeamData data) {
        return new TeamResponse(data.id(), data.name(), data.startsAt());
    }
}
