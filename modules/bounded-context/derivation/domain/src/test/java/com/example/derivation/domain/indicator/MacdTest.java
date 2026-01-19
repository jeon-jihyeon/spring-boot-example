package com.example.derivation.domain.indicator;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class MacdTest {

    @Test
    @DisplayName("derive() - Macd를 Indicator로 변환")
    void derive_convertsToIndicator() {
        // given
        var macd = new Macd(
                new Symbol("KRW-BTC"),
                Timeframe.DAYS,
                new EpochMillis(1000L),
                new BigDecimal("5.12345678"),
                new BigDecimal("4.87654321"),
                new BigDecimal("0.24691357"),
                MacdParams.STANDARD
        );

        // when
        var indicator = macd.derive();

        // then
        assertThat(indicator.symbol().value()).isEqualTo("KRW-BTC");
        assertThat(indicator.timeframe()).isEqualTo(Timeframe.DAYS);
        assertThat(indicator.timestamp().value()).isEqualTo(1000L);
        assertThat(indicator.code()).isEqualTo(Code.MACD);
        assertThat(indicator.values()).containsEntry("value", "5.12345678");
        assertThat(indicator.values()).containsEntry("signal", "4.87654321");
        assertThat(indicator.values()).containsEntry("histogram", "0.24691357");
    }
}
