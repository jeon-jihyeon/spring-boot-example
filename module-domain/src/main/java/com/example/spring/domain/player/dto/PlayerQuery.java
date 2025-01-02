package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.Grade;

public record PlayerQuery(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerQuery from(PlayerData data) {
        return new PlayerQuery(
                data.id().value(),
                data.grade(),
                data.fullName().firstName(),
                data.fullName().lastName(),
                data.teamId().value()
        );
    }
}
