package com.example.core.values;

import java.math.BigDecimal;

public record Amount(BigDecimal value) {
    public Amount {
        if (value == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
    }

    public static Amount from(String value) {
        return new Amount(new BigDecimal(value));
    }

    public static Amount from(double value) {
        return new Amount(BigDecimal.valueOf(value));
    }

    public static Amount zero() {
        return new Amount(BigDecimal.ZERO);
    }
}
