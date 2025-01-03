package com.example.spring.application.api.player.data;

import com.example.spring.application.common.InvalidValueException;
import com.example.spring.domain.command.player.dto.PlayerJoinCommand;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;

public record PlayerJoinRequest(Long teamId) {

    public PlayerJoinRequest {
        if (teamId == null) throw new InvalidValueException();
    }

    public PlayerJoinCommand toCommand(Long playerId) {
        return new PlayerJoinCommand(new PlayerId(playerId), new TeamId(teamId));
    }
}
