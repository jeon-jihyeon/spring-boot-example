package com.example.spring.boot.modules.team.domain.command;

import com.example.spring.boot.modules.team.domain.model.TeamId;
import com.example.spring.boot.modules.team.domain.model.TeamName;

public record TeamUpdate(TeamId id, TeamName name) {
}
