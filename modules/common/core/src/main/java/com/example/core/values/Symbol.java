package com.example.core.values;

public record Symbol(String value) {
    public Symbol {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Symbol cannot be null or blank");
        }
        value = value.toUpperCase();
    }

    public static Symbol from(String value) {
        return new Symbol(value);
    }
}
