package com.example.acquisition.application;

import com.example.acquisition.domain.CandleAggregator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AggregatorConfigTest {

    @Test
    @DisplayName("candleAggregator 빈 생성")
    void candleAggregator_createsCandleAggregatorBean() {
        // given
        var config = new AggregatorConfig();

        // when
        var candleAggregator = config.candleAggregator();

        // then
        assertThat(candleAggregator).isInstanceOf(CandleAggregator.class);
    }
}
