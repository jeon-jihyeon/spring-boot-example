package com.example.derivation.api;

public record IndicatorRequest(String symbol, String currency, long start, long end) {
}
