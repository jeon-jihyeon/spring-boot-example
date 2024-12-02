package com.example.spring.boot.modules.team.api.data;

import com.example.spring.boot.modules.team.domain.model.TeamName;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;

public record TeamCreateRequest(String name) {
    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name));
    }
}
