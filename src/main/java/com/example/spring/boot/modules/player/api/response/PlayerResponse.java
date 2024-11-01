package com.example.spring.boot.modules.player.api.response;

import com.example.spring.boot.modules.player.domain.query.PlayerQuery;

public record PlayerResponse(Long id, String grade, String firstName, String lastName, Long teamId) {
    public static PlayerResponse from(PlayerQuery query) {
        return new PlayerResponse(
                query.id().value(),
                query.grade().name(),
                query.fullName().firstName(),
                query.fullName().lastName(),
                query.teamId().value()
        );
    }
}
