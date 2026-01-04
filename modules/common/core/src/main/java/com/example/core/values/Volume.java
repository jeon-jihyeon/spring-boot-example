package com.example.core.values;

import java.math.BigDecimal;

public record Volume(BigDecimal value) {
    public Volume {
        if (value == null) {
            throw new IllegalArgumentException("Volume cannot be null");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Volume cannot be negative");
        }
    }

    public static Volume from(String value) {
        return new Volume(new BigDecimal(value));
    }

    public static Volume from(double value) {
        return new Volume(BigDecimal.valueOf(value));
    }

    public static Volume zero() {
        return new Volume(BigDecimal.ZERO);
    }

    public Volume add(Volume other) {
        return new Volume(this.value.add(other.value));
    }
}
