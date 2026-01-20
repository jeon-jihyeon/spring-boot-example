package com.example.acquisition.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;

public record GetCandlesRequest(Symbol symbol, Currency currency, EpochMillis start, EpochMillis end) {
    public CandlesQuery toPeriodCandlesQuery() {
        return new CandlesQuery(symbol, currency, start, end);
    }
}
