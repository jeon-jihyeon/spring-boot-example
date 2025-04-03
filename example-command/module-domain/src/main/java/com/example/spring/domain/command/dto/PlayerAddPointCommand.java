package com.example.spring.domain.command.dto;


import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.command.model.PlayerPoint;

public record PlayerAddPointCommand(PlayerId playerId, PlayerPoint playerPoint) {
    public String getKey() {
        return playerId.value().toString();
    }
}
