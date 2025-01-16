package com.example.spring.application.api.team.response;

import com.example.spring.domain.command.team.dto.TeamData;

import java.time.LocalDateTime;

public record TeamCommandResponse(Long id, String name, LocalDateTime startsAt) {
    public static TeamCommandResponse from(TeamData data) {
        return new TeamCommandResponse(data.id().value(), data.name().value(), data.startsAt());
    }
}
