package com.example.derivation.application;

import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.calculator.MacdCalculator;
import com.example.derivation.domain.indicator.MacdParams;
import org.springframework.stereotype.Service;

@Service
public class GetStandardMacd {
    private final CandleFetcher candleFetcher;
    private final MacdCalculator calculator = new MacdCalculator();

    public GetStandardMacd(CandleFetcher candleFetcher) {
        this.candleFetcher = candleFetcher;
    }

    public Indicator execute(IndicatorParam param) {
        var candles = candleFetcher.find(new CandlesRequest(param.symbol(), param.currency(), param.start(), param.end()));
        return calculator.calculate(MacdParams.STANDARD, candles).derive();
    }
}
