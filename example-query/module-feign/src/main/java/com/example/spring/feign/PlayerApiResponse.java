package com.example.spring.feign;

import com.example.spring.domain.query.PlayerQuery;

public record PlayerApiResponse(Long id, String grade, Integer point, String firstName, String lastName, Long teamId) {
    public PlayerQuery toQuery() {
        return new PlayerQuery(id, grade, point, firstName, lastName, teamId);
    }
}