package com.example.derivation.application;

import com.example.derivation.domain.Candle;
import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetIndicators {
    private final CandleFetcher candleFetcher;
    private final EmaCalculator emaCalculator;
    private final MacdCalculator macdCalculator;

    public GetIndicators(
            CandleFetcher candleFetcher,
            EmaCalculator emaCalculator,
            MacdCalculator macdCalculator
    ) {
        this.candleFetcher = candleFetcher;
        this.emaCalculator = emaCalculator;
        this.macdCalculator = macdCalculator;
    }

    public List<Indicator> execute(CandlesRequest request) {
        List<Candle> candles = candleFetcher.find(request);
        return List.of(
                emaCalculator.calculate(candles).derive(),
                macdCalculator.calculate(candles).derive()
        );
    }
}
