package com.example.derivation.application;

import com.example.derivation.domain.Candle;

import java.util.List;

public interface CandleFetcher {
    List<Candle> find(CandlesRequest request);
}
