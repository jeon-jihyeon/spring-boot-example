package com.example.contracts.acquisition;

import com.example.core.values.Symbol;

import java.util.Currency;
import java.util.List;

public record DailyAcquisitionResponse(Symbol symbol, Currency currency, List<HourlyCandleResponse> hourlyCandles) {
}
