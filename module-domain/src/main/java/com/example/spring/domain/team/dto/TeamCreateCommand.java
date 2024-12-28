package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamName;

import java.util.List;

public record TeamCreateCommand(TeamName name, List<PlayerId> playerIds) {
}
