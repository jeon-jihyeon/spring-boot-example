package com.example.spring.domain.command.team.dto;

import com.example.spring.domain.command.team.model.TeamName;

public record TeamCreateCommand(TeamName name) {
}
