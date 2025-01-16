package com.example.spring.application.api.player.request;

import com.example.spring.domain.command.player.dto.PlayerJoinCommand;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import jakarta.validation.constraints.NotNull;

public record PlayerJoinRequest(@NotNull Long teamId) {
    public PlayerJoinCommand toCommand(Long playerId) {
        return new PlayerJoinCommand(new PlayerId(playerId), new TeamId(teamId));
    }
}
