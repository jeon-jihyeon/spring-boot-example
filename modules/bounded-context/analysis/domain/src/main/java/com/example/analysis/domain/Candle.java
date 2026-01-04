package com.example.analysis.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

import java.math.BigDecimal;

public record Candle(Symbol symbol, EpochMillis startTime, OHLCV ohlcv, Timeframe timeframe) {
    private BigDecimal change(Candle previous) {
        return this.ohlcv.close().value().subtract(previous.ohlcv.close().value());
    }

    public BigDecimal gain(Candle previous) {
        BigDecimal change = change(previous);
        return change.compareTo(BigDecimal.ZERO) > 0 ? change : BigDecimal.ZERO;
    }

    public BigDecimal loss(Candle previous) {
        BigDecimal change = change(previous);
        return change.compareTo(BigDecimal.ZERO) < 0 ? change.abs() : BigDecimal.ZERO;
    }
}
