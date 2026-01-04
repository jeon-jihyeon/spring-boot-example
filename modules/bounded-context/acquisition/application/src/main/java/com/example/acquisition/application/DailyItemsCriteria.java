package com.example.acquisition.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;

public record DailyItemsCriteria(Symbol symbol, Currency currency, EpochMillis start, EpochMillis end) {
    public PeriodCandlesQuery toPeriodCandlesQuery() {
        return new PeriodCandlesQuery(symbol, currency, start, end);
    }
}
