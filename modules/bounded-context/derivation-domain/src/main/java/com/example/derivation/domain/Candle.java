package com.example.derivation.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

public record Candle(Symbol symbol, EpochMillis startTime, OHLCV ohlcv, Timeframe timeframe) {
}
