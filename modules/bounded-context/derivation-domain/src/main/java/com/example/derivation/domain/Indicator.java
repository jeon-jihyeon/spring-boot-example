package com.example.derivation.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.domain.indicator.Code;

import java.util.Map;

public record Indicator(
        Symbol symbol,
        Timeframe timeframe,
        EpochMillis timestamp,
        Code code,
        Map<String, String> values
) {
}
