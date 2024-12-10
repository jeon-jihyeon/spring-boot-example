package com.example.spring.boot.modules.team.api.data;

import com.example.spring.boot.modules.team.domain.model.TeamName;
import com.example.spring.boot.modules.team.domain.repository.command.TeamCreateCommand;

import java.util.List;

public record TeamCreateRequest(String name, List<Long> playerIds) {
    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name), playerIds);
    }
}
