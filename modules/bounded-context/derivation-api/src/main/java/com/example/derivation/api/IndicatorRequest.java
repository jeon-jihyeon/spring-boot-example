package com.example.derivation.api;

import com.example.core.exception.InvalidValueException;

public record IndicatorRequest(String symbol, String currency, long start, long end) {
    public IndicatorRequest {
        if (symbol == null || symbol.isBlank()) {
            throw new InvalidValueException("symbol cannot be null or blank");
        }
        if (currency == null || currency.isBlank()) {
            throw new InvalidValueException("currency cannot be null or blank");
        }
        if (start > end) {
            throw new InvalidValueException("start must be less than or equal to end");
        }
    }
}
