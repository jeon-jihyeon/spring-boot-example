package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.core.IdGenerator;

public record PlayerId(Long value) {
    public static PlayerId newId() {
        return new PlayerId(IdGenerator.newId());
    }
}
