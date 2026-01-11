package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.Test;

import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CandleCandleAggregatorTest {

    private static final CandleAggregator candleAggregator = new CandleAggregator();

    private static Candle candle(
            Symbol symbol,
            Currency currency,
            long epochMillis,
            String open,
            String high,
            String low,
            String close,
            String volume,
            String turnover,
            Timeframe timeframe
    ) {
        OHLCV ohlcv = new OHLCV(
                Price.from(open),
                Price.from(high),
                Price.from(low),
                Price.from(close),
                Volume.from(volume),
                Price.from(turnover)
        );
        return new Candle(symbol, currency, EpochMillis.from(epochMillis), ohlcv, timeframe);
    }

    @Test
    void aggregate_groupsByBucketAndAggregatesValues() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");

        // when
        var result = candleAggregator.aggregate(List.of(
                candle(symbol, currency, 0L, "1", "3", "1", "2", "10", "100", Timeframe.MINUTES),
                candle(symbol, currency, 30 * 60_000L, "2", "4", "1.5", "3", "5", "50", Timeframe.MINUTES),
                candle(symbol, currency, 59 * 60_000L, "3", "5", "2", "4", "2", "20", Timeframe.MINUTES),
                candle(symbol, currency, 60 * 60_000L, "4", "6", "3", "5", "1", "10", Timeframe.MINUTES)
        ), Timeframe.HOURS);

        // then
        assertEquals(2, result.size());

        var first = result.get(0);
        assertEquals(EpochMillis.from(0L), first.startTime());
        assertEquals(Price.from("1"), first.ohlcv().open());
        assertEquals(Price.from("5"), first.ohlcv().high());
        assertEquals(Price.from("1"), first.ohlcv().low());
        assertEquals(Price.from("4"), first.ohlcv().close());
        assertEquals(Volume.from("17"), first.ohlcv().volume());
        assertEquals(Price.from("170"), first.ohlcv().turnover());

        var second = result.get(1);
        assertEquals(EpochMillis.from(60 * 60_000L), second.startTime());
        assertEquals(Price.from("4"), second.ohlcv().open());
        assertEquals(Price.from("6"), second.ohlcv().high());
        assertEquals(Price.from("3"), second.ohlcv().low());
        assertEquals(Price.from("5"), second.ohlcv().close());
        assertEquals(Volume.from("1"), second.ohlcv().volume());
        assertEquals(Price.from("10"), second.ohlcv().turnover());
    }

    @Test
    void aggregate_throwsWhenTimeframeIsMixed() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");

        // when
        assertThrows(TimeframeMismatchException.class, () -> candleAggregator.aggregate(List.of(
                candle(symbol, currency, 0L, "1", "1", "1", "1", "1", "1", Timeframe.MINUTES),
                candle(symbol, currency, 60_000L, "1", "1", "1", "1", "1", "1", Timeframe.HOURS)
        ), Timeframe.HOURS));
    }

    @Test
    void aggregate_throwsWhenInputIsNotSmallerThanTarget() {
        // given
        var symbol = Symbol.from("BTC");
        var currency = Currency.getInstance("USD");

        // when
        assertThrows(TimeframeHierarchyException.class, () -> candleAggregator.aggregate(
                List.of(candle(symbol, currency, 0L, "1", "1", "1", "1", "1", "1", Timeframe.HOURS)),
                Timeframe.MINUTES
        ));
    }
}
