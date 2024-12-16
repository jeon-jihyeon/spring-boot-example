package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.TeamName;

import java.util.List;

public record TeamCreateCommand(TeamName name, List<PlayerId> playerIds) {
}
