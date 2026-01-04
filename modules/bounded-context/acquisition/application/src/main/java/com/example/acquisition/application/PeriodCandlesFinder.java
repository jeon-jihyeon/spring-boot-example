package com.example.acquisition.application;

import com.example.acquisition.domain.Candle;

import java.util.List;

public interface PeriodCandlesFinder {
    List<Candle> find(PeriodCandlesQuery query);
}
