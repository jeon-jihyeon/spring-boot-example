package com.example.spring.domain.command.team.dto;

import com.example.spring.domain.command.team.model.TeamId;

public record TeamDeleteEvent(TeamId teamId) {
    public static TeamDeleteEvent from(TeamDeleteCommand command) {
        return new TeamDeleteEvent(command.teamId());
    }
}
