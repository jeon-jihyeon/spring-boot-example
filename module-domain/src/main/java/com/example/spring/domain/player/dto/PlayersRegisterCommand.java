package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;

import java.util.List;

public record PlayersRegisterCommand(TeamId teamId, List<PlayerId> playerIds) {
}
