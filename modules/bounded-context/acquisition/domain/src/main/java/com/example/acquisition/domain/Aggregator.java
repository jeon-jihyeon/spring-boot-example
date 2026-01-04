package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public final class Aggregator<T extends Aggregatable<T>> {
    public Aggregator() {
    }

    public List<T> aggregate(List<T> aggregatables, Timeframe target) {
        if (aggregatables == null || aggregatables.isEmpty()) {
            return List.of();
        }

        Timeframe first = aggregatables.get(0).timeframe();
        if (first.getSeconds() >= target.getSeconds()) {
            throw new TimeframeHierarchyException(target, first);
        }

        for (T aggregatable : aggregatables) {
            if (aggregatable.timeframe() != first) {
                throw new TimeframeMismatchException(first, aggregatable.timeframe());
            }
        }

        long bucketMillis = target.getSeconds() * 1000L;
        return aggregatables.stream()
                .sorted()
                .collect(Collectors.groupingBy(
                        c -> c.epochMillis() / bucketMillis,
                        LinkedHashMap::new,
                        Collectors.toList()
                )).values().stream()
                .map(bucket -> bucket.stream().reduce(Aggregatable::aggregate).orElseThrow())
                .toList();
    }
}
