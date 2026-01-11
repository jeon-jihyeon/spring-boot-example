package com.example.contract.acquisition;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

import java.util.Currency;

public record AcquisitionCandleResponse(
        Symbol symbol,
        Currency currency,
        EpochMillis startTime,
        OHLCV ohlcv,
        Timeframe timeframe
) {
}
