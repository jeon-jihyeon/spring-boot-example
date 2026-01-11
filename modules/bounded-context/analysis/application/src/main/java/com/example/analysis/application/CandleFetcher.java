package com.example.analysis.application;

import com.example.analysis.domain.Candle;

import java.util.List;

public interface CandleFetcher {
    List<Candle> find(CandlesRequest request);
}
