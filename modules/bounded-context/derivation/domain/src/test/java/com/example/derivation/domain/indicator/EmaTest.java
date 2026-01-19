package com.example.derivation.domain.indicator;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class EmaTest {

    @Test
    @DisplayName("derive() - Ema를 Indicator로 변환")
    void derive_convertsToIndicator() {
        // given
        var ema = new Ema(
                new Symbol("KRW-BTC"),
                Timeframe.DAYS,
                new EpochMillis(1000L),
                new BigDecimal("150.12345678"),
                12
        );

        // when
        var indicator = ema.derive();

        // then
        assertThat(indicator.symbol().value()).isEqualTo("KRW-BTC");
        assertThat(indicator.timeframe()).isEqualTo(Timeframe.DAYS);
        assertThat(indicator.timestamp().value()).isEqualTo(1000L);
        assertThat(indicator.code()).isEqualTo(Code.EMA);
        assertThat(indicator.values()).containsEntry("value", "150.12345678");
        assertThat(indicator.values()).containsEntry("period", "12");
    }
}
