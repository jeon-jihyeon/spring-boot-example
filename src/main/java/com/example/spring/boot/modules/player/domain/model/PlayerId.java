package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.IdGenerator;
import com.example.spring.boot.core.exception.InvalidValueException;

public record PlayerId(Long value) {
    public PlayerId {
        if (value == null || value < 0) throw new InvalidValueException();
    }

    public static PlayerId newId() {
        return new PlayerId(IdGenerator.newId());
    }
}
