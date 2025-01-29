package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.player.model.PlayerPoint;

public record PlayerPointCommand(PlayerId playerId, PlayerPoint playerPoint) {
    public String getKey() {
        return playerId.value().toString();
    }
}
