package com.example.derivation.api;

import com.example.derivation.domain.Indicator;

import java.util.Map;

public record IndicatorResponse(
        String symbol,
        String timeframe,
        long timestamp,
        String code,
        Map<String, String> values
) {
    public static IndicatorResponse from(Indicator indicator) {
        return new IndicatorResponse(
                indicator.symbol().value(),
                indicator.timeframe().name(),
                indicator.timestamp().value(),
                indicator.code().name(),
                indicator.values()
        );
    }
}
