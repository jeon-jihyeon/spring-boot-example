package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.core.IdGenerator;

public record TeamId(Long value) {
    public static TeamId newId() {
        return new TeamId(IdGenerator.newId());
    }
}
