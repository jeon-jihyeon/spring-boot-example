package com.example.derivation.application;

import com.example.derivation.domain.Candle;
import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import com.example.derivation.domain.indicator.MacdParams;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetIndicators {
    private final CandleFetcher candleFetcher;
    private final EmaCalculator emaCalculator = new EmaCalculator(12);
    private final MacdCalculator macdCalculator = new MacdCalculator(MacdParams.STANDARD);

    public GetIndicators(CandleFetcher candleFetcher) {
        this.candleFetcher = candleFetcher;
    }

    public List<Indicator> execute(CandlesRequest request) {
        List<Candle> candles = candleFetcher.find(request);
        return List.of(
                emaCalculator.calculate(candles).derive(),
                macdCalculator.calculate(candles).derive()
        );
    }
}
