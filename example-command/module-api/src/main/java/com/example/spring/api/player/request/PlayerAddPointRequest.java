package com.example.spring.api.player.request;

import com.example.spring.domain.command.dto.PlayerAddPointCommand;
import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.command.model.PlayerPoint;
import jakarta.validation.constraints.NotNull;

public record PlayerAddPointRequest(@NotNull Integer point) {
    public PlayerAddPointCommand toCommand(Long playerId) {
        return new PlayerAddPointCommand(new PlayerId(playerId), new PlayerPoint(point));
    }
}
