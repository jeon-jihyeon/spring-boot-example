package com.example.derivation.domain.calculator;

import com.example.derivation.domain.Candle;
import com.example.derivation.domain.indicator.Ema;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Exponential Moving Average (지수 이동 평균) 계산기
 * <p>
 * 최근 데이터에 더 많은 가중치를 부여하는 이동평균
 * EMA = (현재가 × 승수) + (이전 EMA × (1 - 승수))
 * 승수 = 2 / (period + 1)
 * <p>
 * 첫 EMA는 SMA로 시작
 */
public final class EmaCalculator {
    private static final int SCALE = 8;

    public Ema calculate(int period, List<Candle> candles) {
        if (candles == null || candles.isEmpty()) {
            throw new InsufficientDataException("Candles cannot be null or empty");
        }

        if (candles.size() < period) {
            var msg = String.format("Not enough data. Required: %d, Provided: %d", period, candles.size());
            throw new InsufficientDataException(msg);
        }

        BigDecimal multiplier = BigDecimal.valueOf(2)
                .divide(BigDecimal.valueOf(period + 1), SCALE, RoundingMode.HALF_UP);

        BigDecimal sum = candles.subList(0, period).stream()
                .map(candle -> candle.ohlcv().close().value())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal ema = sum.divide(BigDecimal.valueOf(period), SCALE, RoundingMode.HALF_UP);
        for (int i = period; i < candles.size(); i++) {
            BigDecimal currentPrice = candles.get(i).ohlcv().close().value();

            // EMA = (현재가 × 승수) + (이전 EMA × (1 - 승수))
            ema = currentPrice.multiply(multiplier)
                    .add(ema.multiply(BigDecimal.ONE.subtract(multiplier)))
                    .setScale(SCALE, RoundingMode.HALF_UP);
        }

        Candle latest = candles.get(candles.size() - 1);
        return new Ema(latest.symbol(), latest.timeframe(), latest.startTime(), ema, period);
    }
}
