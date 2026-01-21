package com.example.derivation.api;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndicatorRequestTest {

    @Test
    @DisplayName("생성 - 유효한 값으로 생성")
    void constructor_validValues_createsRequest() {
        // given
        var symbol = "BTC";
        var currency = "USD";
        var start = 1000L;
        var end = 2000L;

        // when
        var request = new IndicatorRequest(symbol, currency, start, end);

        // then
        assertThat(request.symbol()).isEqualTo(symbol);
        assertThat(request.currency()).isEqualTo(currency);
        assertThat(request.start()).isEqualTo(start);
        assertThat(request.end()).isEqualTo(end);
    }
}
