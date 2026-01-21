package com.example.derivation.domain;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.domain.indicator.Code;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IndicatorTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsIndicator() {
        // given
        var symbol = Symbol.from("BTC");
        var timeframe = Timeframe.DAYS;
        var timestamp = EpochMillis.from(1000L);
        var code = Code.EMA;
        var values = Map.of("value", "123.45", "period", "14");

        // when
        var indicator = new Indicator(symbol, timeframe, timestamp, code, values);

        // then
        assertThat(indicator.symbol()).isEqualTo(symbol);
        assertThat(indicator.timeframe()).isEqualTo(timeframe);
        assertThat(indicator.timestamp()).isEqualTo(timestamp);
        assertThat(indicator.code()).isEqualTo(code);
        assertThat(indicator.values()).isEqualTo(values);
    }

    @Test
    @DisplayName("생성 - MACD 지표")
    void constructor_macdIndicator_createsIndicator() {
        // given
        var symbol = Symbol.from("ETH");
        var timeframe = Timeframe.HOURS;
        var timestamp = EpochMillis.from(2000L);
        var code = Code.MACD;
        var values = Map.of(
                "macd", "0.5",
                "signal", "0.3",
                "histogram", "0.2"
        );

        // when
        var indicator = new Indicator(symbol, timeframe, timestamp, code, values);

        // then
        assertThat(indicator.code()).isEqualTo(Code.MACD);
        assertThat(indicator.values()).containsEntry("macd", "0.5");
        assertThat(indicator.values()).containsEntry("signal", "0.3");
        assertThat(indicator.values()).containsEntry("histogram", "0.2");
    }
}
