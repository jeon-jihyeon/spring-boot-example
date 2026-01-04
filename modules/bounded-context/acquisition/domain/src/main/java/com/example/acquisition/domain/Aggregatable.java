package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;

public interface Aggregatable<T extends Aggregatable<T>> extends Comparable<T> {
    T aggregate(T b);

    Long epochMillis();

    Timeframe timeframe();
}
