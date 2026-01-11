package com.example.acquisition.application;

import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;


public record CandlesQuery(Symbol symbol, Currency currency, EpochMillis start, EpochMillis end) {
}
