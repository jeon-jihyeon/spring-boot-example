package com.example.analysis.application;

import com.example.analysis.domain.Candle;
import com.example.core.values.Amount;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

import java.util.Currency;
import java.util.List;

public record DailyItem(
        Symbol symbol, Currency currency, EpochMillis timestamp,
        Amount marketCap, Amount circulatingSupply,
        Amount hashrate, Amount difficulty, Amount minerRevenue,
        List<Candle> hourlyCandles
) {
}
