package com.example.acquisition.application;

import com.example.acquisition.domain.Candle;

import java.util.List;

public interface CandlesFinder {
    List<Candle> find(CandlesQuery query);
}
