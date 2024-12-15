package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.dto.PlayerData;

public record PlayerResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerResponse from(PlayerData data) {
        return new PlayerResponse(data.id(), data.grade(), data.firstName(), data.lastName(), data.teamId());
    }
}
