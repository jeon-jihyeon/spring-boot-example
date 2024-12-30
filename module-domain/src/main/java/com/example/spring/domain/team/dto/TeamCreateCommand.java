package com.example.spring.domain.team.dto;

import com.example.spring.domain.team.model.TeamName;

public record TeamCreateCommand(TeamName name) {
}
