package com.example.spring.domain.command.player.model;

import com.example.spring.domain.IdGenerator;

public record PlayerId(Long value) {
    public static PlayerId newId() {
        return new PlayerId(IdGenerator.newId());
    }
}
