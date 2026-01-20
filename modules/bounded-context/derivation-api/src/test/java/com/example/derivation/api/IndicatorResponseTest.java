package com.example.derivation.api;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;
import com.example.derivation.domain.Indicator;
import com.example.derivation.domain.indicator.Code;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class IndicatorResponseTest {

    @Test
    @DisplayName("from() - Indicator를 IndicatorResponse로 변환")
    void from_convertsIndicatorToResponse() {
        // given
        var indicator = new Indicator(
                new Symbol("KRW-BTC"),
                Timeframe.DAYS,
                new EpochMillis(1000L),
                Code.EMA,
                Map.of("value", "150.123", "period", "12")
        );

        // when
        var response = IndicatorResponse.from(indicator);

        // then
        assertThat(response.symbol()).isEqualTo("KRW-BTC");
        assertThat(response.timeframe()).isEqualTo("DAYS");
        assertThat(response.timestamp()).isEqualTo(1000L);
        assertThat(response.code()).isEqualTo("EMA");
        assertThat(response.values()).containsEntry("value", "150.123");
        assertThat(response.values()).containsEntry("period", "12");
    }
}
