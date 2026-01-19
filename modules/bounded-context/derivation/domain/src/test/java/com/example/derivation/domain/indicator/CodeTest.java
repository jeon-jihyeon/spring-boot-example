package com.example.derivation.domain.indicator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CodeTest {

    @Test
    @DisplayName("EMA의 fullName 반환")
    void ema_getFullName() {
        assertThat(Code.EMA.getFullName()).isEqualTo("Exponential Moving Average");
    }

    @Test
    @DisplayName("MACD의 fullName 반환")
    void macd_getFullName() {
        assertThat(Code.MACD.getFullName()).isEqualTo("Moving Average Convergence Divergence");
    }
}
