package com.example.acquisition.application;

import com.example.acquisition.domain.Candle;
import com.example.acquisition.domain.CandleAggregator;
import com.example.core.enums.Timeframe;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCandles {
    private final CandleAggregator candleAggregator = new CandleAggregator();
    private final CandlesFinder candlesFinder;

    public GetCandles(CandlesFinder candlesFinder) {
        this.candlesFinder = candlesFinder;
    }

    public List<Candle> execute(GetCandlesRequest criteria) {
        List<Candle> candles = candlesFinder.find(criteria.toPeriodCandlesQuery());
        return candleAggregator.aggregate(candles, Timeframe.HOURS);
    }
}
