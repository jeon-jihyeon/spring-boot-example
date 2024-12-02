package com.example.spring.boot.modules.team.domain.event;

import com.example.spring.boot.modules.team.domain.model.TeamId;

public record TeamDeleteEvent(Long teamId) {
    public static TeamDeleteEvent from(TeamId teamId) {
        return new TeamDeleteEvent(teamId.value());
    }
}
