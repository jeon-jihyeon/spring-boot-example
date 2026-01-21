package com.example.core.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TimeframeTest {

    @Test
    @DisplayName("MINUTES - 60초")
    void minutes_returns60Seconds() {
        assertThat(Timeframe.MINUTES.getSeconds()).isEqualTo(60);
    }

    @Test
    @DisplayName("HOURS - 3600초")
    void hours_returns3600Seconds() {
        assertThat(Timeframe.HOURS.getSeconds()).isEqualTo(3600);
    }

    @Test
    @DisplayName("DAYS - 86400초")
    void days_returns86400Seconds() {
        assertThat(Timeframe.DAYS.getSeconds()).isEqualTo(86400);
    }

    @Test
    @DisplayName("WEEKS - 604800초")
    void weeks_returns604800Seconds() {
        assertThat(Timeframe.WEEKS.getSeconds()).isEqualTo(604800);
    }

    @Test
    @DisplayName("모든 Timeframe 값 존재 확인")
    void values_containsAllExpectedValues() {
        assertThat(Timeframe.values())
                .containsExactly(Timeframe.MINUTES, Timeframe.HOURS, Timeframe.DAYS, Timeframe.WEEKS);
    }

    @Test
    @DisplayName("valueOf - 문자열로 Timeframe 조회")
    void valueOf_validString_returnsTimeframe() {
        assertThat(Timeframe.valueOf("MINUTES")).isEqualTo(Timeframe.MINUTES);
        assertThat(Timeframe.valueOf("HOURS")).isEqualTo(Timeframe.HOURS);
        assertThat(Timeframe.valueOf("DAYS")).isEqualTo(Timeframe.DAYS);
        assertThat(Timeframe.valueOf("WEEKS")).isEqualTo(Timeframe.WEEKS);
    }
}
