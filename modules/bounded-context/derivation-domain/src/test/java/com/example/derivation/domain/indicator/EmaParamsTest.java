package com.example.derivation.domain.indicator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EmaParamsTest {

    @Test
    @DisplayName("SHORT - period 9")
    void short_period() {
        assertThat(EmaParams.SHORT.period()).isEqualTo(9);
    }

    @Test
    @DisplayName("STANDARD - period 12")
    void standard_period() {
        assertThat(EmaParams.STANDARD.period()).isEqualTo(12);
    }

    @Test
    @DisplayName("LONG - period 26")
    void long_period() {
        assertThat(EmaParams.LONG.period()).isEqualTo(26);
    }
}
