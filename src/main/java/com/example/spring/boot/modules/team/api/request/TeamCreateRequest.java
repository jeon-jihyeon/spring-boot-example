package com.example.spring.boot.modules.team.api.request;

import com.example.spring.boot.modules.team.domain.command.TeamCreateCommand;
import com.example.spring.boot.modules.team.domain.model.TeamName;

public record TeamCreateRequest(String name) {
    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name));
    }
}
