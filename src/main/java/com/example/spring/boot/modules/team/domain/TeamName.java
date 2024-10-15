package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.core.exceptions.InvalidValueException;

public record TeamName(String value) {
    public TeamName {
        if (value == null || value.length() > 30) throw new InvalidValueException();
    }
}
