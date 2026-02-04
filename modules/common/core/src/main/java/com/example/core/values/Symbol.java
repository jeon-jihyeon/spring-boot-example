package com.example.core.values;

import com.example.core.exception.InvalidValueException;

public record Symbol(String value) {
    public Symbol {
        if (value == null || value.isBlank()) {
            throw new InvalidValueException("Symbol cannot be null or blank");
        }
        value = value.toUpperCase();
    }

    public static Symbol from(String value) {
        return new Symbol(value);
    }
}
