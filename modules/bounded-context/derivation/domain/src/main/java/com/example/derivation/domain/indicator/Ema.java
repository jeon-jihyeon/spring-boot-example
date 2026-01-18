package com.example.derivation.domain.indicator;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.domain.Indicator;

import java.math.BigDecimal;
import java.util.Map;

public record Ema(
        Symbol symbol,
        Timeframe timeframe,
        EpochMillis timestamp,
        BigDecimal value,
        int period
) {
    public Indicator derive() {
        return new Indicator(
                symbol,
                timeframe,
                timestamp,
                Code.EMA,
                Map.of("value", value.toPlainString(), "period", String.valueOf(period))
        );
    }
}
