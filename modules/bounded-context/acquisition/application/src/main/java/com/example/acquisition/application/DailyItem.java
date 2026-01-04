package com.example.acquisition.application;

import com.example.core.values.Symbol;

import java.util.Currency;
import java.util.List;

// TODO: Fundamental values to be added later
public record DailyItem(Symbol symbol, Currency currency, List<HourlyCandle> hourlyCandles) {
}
