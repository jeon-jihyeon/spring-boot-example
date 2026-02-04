package com.example.derivation.api;

public record IndicatorRequest(String symbol, String currency, long start, long end) {
    public IndicatorRequest {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("symbol cannot be null or blank");
        }
        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("currency cannot be null or blank");
        }
        if (start > end) {
            throw new IllegalArgumentException("start must be less than or equal to end");
        }
    }
}
