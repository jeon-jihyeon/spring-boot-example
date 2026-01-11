package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.OHLCV;
import com.example.core.values.Symbol;

import java.util.Currency;

public record Candle(
        Symbol symbol,
        Currency currency,
        EpochMillis startTime,
        OHLCV ohlcv,
        Timeframe timeframe
) implements Comparable<Candle> {
    public Candle {
        if (symbol == null || currency == null || startTime == null || ohlcv == null || timeframe == null) {
            throw new IllegalArgumentException("Candle fields cannot be null");
        }
    }

    public Candle merge(Candle other) {
        Candle before = this.startTime().value() < other.startTime().value() ? this : other;
        Candle after = before == this ? other : this;
        OHLCV aggregated = new OHLCV(
                before.ohlcv.open(),
                before.ohlcv.high().isGreaterThan(after.ohlcv.high()) ? before.ohlcv.high() : after.ohlcv.high(),
                before.ohlcv.low().isLessThan(after.ohlcv.low()) ? before.ohlcv.low() : after.ohlcv.low(),
                after.ohlcv.close(),
                before.ohlcv.volume().add(after.ohlcv.volume()),
                before.ohlcv.turnover().add(after.ohlcv.turnover())
        );
        return new Candle(before.symbol, before.currency, before.startTime, aggregated, this.timeframe);
    }

    public int compareTo(Candle other) {
        return Long.compare(this.startTime().value(), other.startTime().value());
    }
}
