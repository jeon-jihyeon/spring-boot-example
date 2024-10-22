package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.exceptions.InvalidValueException;

public record FullName(String firstName, String lastName) {
    public FullName {
        if (firstName == null || firstName.length() > 30) throw new InvalidValueException();
        if (lastName == null || lastName.length() > 30) throw new InvalidValueException();
    }
}
