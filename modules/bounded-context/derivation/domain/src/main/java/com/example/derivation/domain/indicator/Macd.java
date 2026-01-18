package com.example.derivation.domain.indicator;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.domain.Indicator;

import java.math.BigDecimal;
import java.util.Map;

public record Macd(
        Symbol symbol,
        Timeframe timeframe,
        EpochMillis timestamp,
        BigDecimal value,
        BigDecimal signal,
        BigDecimal histogram,
        MacdParams params
) {
    public Indicator derive() {
        return new Indicator(
                symbol,
                timeframe,
                timestamp,
                Code.MACD,
                Map.of(
                        "value", value.toPlainString(),
                        "signal", signal.toPlainString(),
                        "histogram", histogram.toPlainString()
                )
        );
    }
}
