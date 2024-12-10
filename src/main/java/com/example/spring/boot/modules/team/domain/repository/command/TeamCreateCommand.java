package com.example.spring.boot.modules.team.domain.repository.command;

import com.example.spring.boot.modules.team.domain.model.TeamName;

import java.util.List;

public record TeamCreateCommand(TeamName name, List<Long> playerIds) {
}
