package com.example.contracts.acquisition;

import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

import java.util.Currency;

public record HourlyCandleResponse(Symbol symbol, Currency currency, EpochMillis startTime, OHLCV ohlcv) {
}
