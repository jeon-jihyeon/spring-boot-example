package com.example.spring.application.api.player.request;

import com.example.spring.domain.command.player.dto.PlayerPointCommand;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.player.model.PlayerPoint;
import jakarta.validation.constraints.NotNull;

public record PlayerPointRequest(@NotNull Integer point) {
    public PlayerPointCommand toCommand(Long playerId) {
        return new PlayerPointCommand(new PlayerId(playerId), new PlayerPoint(point));
    }
}
