package com.example.derivation.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class CandleTest {

    @Test
    @DisplayName("gain - 가격 상승 시 상승분 반환")
    void gain_priceUp_returnsGain() {
        // given
        var previous = createCandle(100.0);
        var current = createCandle(150.0);

        // when
        var gain = current.gain(previous);

        // then
        assertThat(gain).isEqualByComparingTo("50.0");
    }

    @Test
    @DisplayName("gain - 가격 하락 시 0 반환")
    void gain_priceDown_returnsZero() {
        // given
        var previous = createCandle(150.0);
        var current = createCandle(100.0);

        // when
        var gain = current.gain(previous);

        // then
        assertThat(gain).isEqualByComparingTo(BigDecimal.ZERO);
    }

    @Test
    @DisplayName("loss - 가격 하락 시 손실분 반환 (절대값)")
    void loss_priceDown_returnsLoss() {
        // given
        var previous = createCandle(150.0);
        var current = createCandle(100.0);

        // when
        var loss = current.loss(previous);

        // then
        assertThat(loss).isEqualByComparingTo("50.0");
    }

    @Test
    @DisplayName("loss - 가격 상승 시 0 반환")
    void loss_priceUp_returnsZero() {
        // given
        var previous = createCandle(100.0);
        var current = createCandle(150.0);

        // when
        var loss = current.loss(previous);

        // then
        assertThat(loss).isEqualByComparingTo(BigDecimal.ZERO);
    }

    private Candle createCandle(double closePrice) {
        return new Candle(
                new Symbol("KRW-BTC"),
                new EpochMillis(1000L),
                new OHLCV(
                        Price.from(closePrice - 5),
                        Price.from(closePrice + 5),
                        Price.from(closePrice - 10),
                        Price.from(closePrice),
                        Volume.from(1000),
                        Price.from(5000)
                ),
                Timeframe.DAYS
        );
    }
}
