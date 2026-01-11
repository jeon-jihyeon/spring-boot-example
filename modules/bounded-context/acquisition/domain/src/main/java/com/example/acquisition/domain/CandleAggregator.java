package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class CandleAggregator {
    public CandleAggregator() {
    }

    public List<Candle> aggregate(List<Candle> candles, Timeframe target) {
        if (candles == null || candles.isEmpty()) {
            return List.of();
        }

        Timeframe first = candles.get(0).timeframe();
        if (first.getSeconds() >= target.getSeconds()) {
            throw new TimeframeHierarchyException(target, first);
        }

        for (Candle candle : candles) {
            if (candle.timeframe() != first) {
                throw new TimeframeMismatchException(first, candle.timeframe());
            }
        }

        long bucketMillis = target.getSeconds() * 1000L;
        return candles.stream()
                .sorted()
                .collect(Collectors.groupingBy(
                        c -> c.startTime().value() / bucketMillis,
                        LinkedHashMap::new,
                        Collectors.toList()
                )).values().stream()
                .map(bucket -> bucket.stream().reduce(Candle::merge).orElseThrow())
                .toList();
    }
}
