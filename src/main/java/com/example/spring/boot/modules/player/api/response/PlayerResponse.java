package com.example.spring.boot.modules.player.api.response;

import com.example.spring.boot.modules.player.domain.model.Grade;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerQuery;

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
