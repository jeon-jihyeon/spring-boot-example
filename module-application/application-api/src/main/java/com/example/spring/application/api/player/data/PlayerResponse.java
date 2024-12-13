package com.example.spring.application.api.player.data;

import com.example.spring.domain.player.Grade;
import com.example.spring.domain.player.repository.query.PlayerQuery;

public record PlayerResponse(Long id, Grade grade, String firstName, String lastName, Long teamId) {
    public static PlayerResponse from(PlayerQuery query) {
        return new PlayerResponse(
                query.id(),
                query.grade(),
                query.firstName(),
                query.lastName(),
                query.teamId()
        );
    }
}
