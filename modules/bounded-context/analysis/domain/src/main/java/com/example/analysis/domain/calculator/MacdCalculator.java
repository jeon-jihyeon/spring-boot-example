package com.example.analysis.domain.calculator;

import com.example.analysis.domain.Candle;
import com.example.analysis.domain.indicator.Core;
import com.example.analysis.domain.indicator.Macd;
import com.example.analysis.domain.indicator.MacdParams;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * Moving Average Convergence Divergence (MACD) 계산기
 * <p>
 * 두 개의 EMA 차이를 이용하여 추세 전환과 모멘텀을 파악하는 지표
 * <p>
 * 계산 과정:
 * 1. Fast EMA (기본 12일) 계산
 * 2. Slow EMA (기본 26일) 계산
 * 3. MACD Line = Fast EMA - Slow EMA
 * 4. Signal Line = MACD Line의 EMA (기본 9일)
 * 5. Histogram = MACD Line - Signal Line
 * <p>
 * 반환값:
 * - value: MACD Line
 * - params: fast, slow, signal 기간과 signalLine, histogram 값
 */
public final class MacdCalculator {
    private static final int SCALE = 8;
    private final MacdParams params;
    private final EmaCalculator fastEmaCalculator;
    private final EmaCalculator slowEmaCalculator;

    public MacdCalculator(MacdParams params) {
        this.params = params;
        this.fastEmaCalculator = new EmaCalculator(params.fast());
        this.slowEmaCalculator = new EmaCalculator(params.slow());
    }

    public Macd calculate(List<Candle> candles) {
        if (candles == null || candles.isEmpty()) {
            throw new IllegalArgumentException("Candles cannot be null or empty");
        }

        // MACD 계산을 위한 최소 데이터: slowPeriod + signalPeriod - 1
        var requiredData = params.slow() + params.signal() - 1;
        if (candles.size() < requiredData) {
            var msg = String.format("Not enough data. Required: %d, Provided: %d", requiredData, candles.size());
            throw new IllegalArgumentException(msg);
        }

        var fastEma = fastEmaCalculator.calculate(candles).value();
        var slowEma = slowEmaCalculator.calculate(candles).value();
        var macdLine = fastEma.subtract(slowEma);

        // MACD Line의 EMA를 계산하기 위해 MACD Line들을 모아야 함
        var macdLines = calculateMacdLines(params, candles);
        var signalLine = calculateEmaFromValues(params.signal(), macdLines);
        var histogram = macdLine.subtract(signalLine);

        Candle latest = candles.get(candles.size() - 1);
        var core = new Core(latest.symbol(), latest.timeframe(), latest.startTime());
        return new Macd(core, macdLine, signalLine, histogram, params);
    }

    private List<BigDecimal> calculateMacdLines(MacdParams params, List<Candle> candles) {
        var macdLines = new ArrayList<BigDecimal>();

        // slowPeriod부터 MACD Line 계산 가능
        for (int i = params.slow() - 1; i < candles.size(); i++) {
            var subCandles = candles.subList(0, i + 1);
            var fastEma = fastEmaCalculator.calculate(subCandles).value();
            var slowEma = slowEmaCalculator.calculate(subCandles).value();
            macdLines.add(fastEma.subtract(slowEma));
        }
        return macdLines;
    }

    private BigDecimal calculateEmaFromValues(int period, List<BigDecimal> values) {
        var multiplier = BigDecimal.valueOf(2).divide(BigDecimal.valueOf(period + 1), SCALE, RoundingMode.HALF_UP);
        var sum = values.subList(0, period).stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        var ema = sum.divide(BigDecimal.valueOf(period), SCALE, RoundingMode.HALF_UP);

        for (int i = period; i < values.size(); i++) {
            var currentValue = values.get(i);
            ema = currentValue.multiply(multiplier)
                    .add(ema.multiply(BigDecimal.ONE.subtract(multiplier)))
                    .setScale(SCALE, RoundingMode.HALF_UP);
        }
        return ema;
    }
}
