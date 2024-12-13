package com.example.spring.application.api.team.data;

import com.example.spring.domain.team.TeamId;

public record TeamCreateResponse(Long id) {
    public static TeamCreateResponse from(TeamId teamId) {
        return new TeamCreateResponse(teamId.value());
    }
}
