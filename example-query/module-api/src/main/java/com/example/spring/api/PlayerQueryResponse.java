package com.example.spring.api;

import com.example.spring.domain.query.PlayerQuery;

public record PlayerQueryResponse(
        Long id,
        String grade,
        Integer point,
        String firstName,
        String lastName,
        Long teamId
) {
    public static PlayerQueryResponse from(PlayerQuery query) {
        return new PlayerQueryResponse(
                query.id(),
                query.grade(),
                query.point(),
                query.firstName(),
                query.lastName(),
                query.teamId()
        );
    }
}
