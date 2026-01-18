package com.example.derivation.domain.indicator;

public enum Code {
    EMA("Exponential Moving Average"),
    MACD("Moving Average Convergence Divergence");

    private final String fullName;

    Code(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
