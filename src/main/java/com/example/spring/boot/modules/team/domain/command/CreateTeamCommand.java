package com.example.spring.boot.modules.team.domain.command;

import com.example.spring.boot.modules.team.domain.model.TeamName;

public record CreateTeamCommand(TeamName name) {
}
