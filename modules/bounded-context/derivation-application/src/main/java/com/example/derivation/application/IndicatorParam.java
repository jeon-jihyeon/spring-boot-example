package com.example.derivation.application;

import com.example.core.exception.InvalidValueException;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;

public record IndicatorParam(Symbol symbol, Currency currency, EpochMillis start, EpochMillis end) {
    public IndicatorParam {
        if (symbol == null || currency == null || start == null || end == null) {
            throw new InvalidValueException("symbol and range cannot be null");
        }
    }
}
