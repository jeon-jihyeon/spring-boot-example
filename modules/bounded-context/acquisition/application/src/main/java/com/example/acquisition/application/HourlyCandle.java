package com.example.acquisition.application;

import com.example.acquisition.domain.Candle;
import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

import java.util.Currency;

public record HourlyCandle(Symbol symbol, Currency currency, EpochMillis startTime, OHLCV ohlcv) {
    public static HourlyCandle from(Candle candle) {
        return new HourlyCandle(candle.symbol(), candle.currency(), candle.startTime(), candle.ohlcv());
    }
}
