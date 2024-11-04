package com.example.spring.boot.modules.team.domain.repository.command;

import com.example.spring.boot.modules.team.domain.model.TeamName;

public record TeamCreateCommand(TeamName name) {
}
