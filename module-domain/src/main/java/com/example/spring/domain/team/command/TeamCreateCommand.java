package com.example.spring.domain.team.command;

import com.example.spring.domain.team.TeamName;

import java.util.List;

public record TeamCreateCommand(TeamName name, List<Long> playerIds) {
}
