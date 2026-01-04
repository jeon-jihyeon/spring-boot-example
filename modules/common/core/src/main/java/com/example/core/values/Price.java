package com.example.core.values;

import java.math.BigDecimal;

public record Price(BigDecimal value) {
    public Price {
        if (value == null) {
            throw new IllegalArgumentException("Price cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public static Price from(String value) {
        return new Price(new BigDecimal(value));
    }

    public static Price from(double value) {
        return new Price(BigDecimal.valueOf(value));
    }

    public static Price zero() {
        return new Price(BigDecimal.ZERO);
    }

    public boolean isLessThan(Price other) {
        return this.value.compareTo(other.value) < 0;
    }

    public boolean isGreaterThan(Price other) {
        return this.value.compareTo(other.value) > 0;
    }

    public Price add(Price other) {
        return new Price(this.value.add(other.value));
    }
}
