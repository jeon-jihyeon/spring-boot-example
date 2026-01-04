package com.example.analysis.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;

public record DailyItemsRequest(Symbol symbol, Currency currency, EpochMillis start, EpochMillis end) {
    public DailyItemsRequest {
        if (symbol == null || currency == null || start == null || end == null) {
            throw new IllegalArgumentException("symbol and range cannot be null");
        }
    }
}
