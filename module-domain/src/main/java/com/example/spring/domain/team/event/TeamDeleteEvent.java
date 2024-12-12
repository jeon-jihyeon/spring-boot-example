package com.example.spring.domain.team.event;

import com.example.spring.domain.team.TeamId;

public record TeamDeleteEvent(Long teamId) {
    public static TeamDeleteEvent from(TeamId teamId) {
        return new TeamDeleteEvent(teamId.value());
    }
}
