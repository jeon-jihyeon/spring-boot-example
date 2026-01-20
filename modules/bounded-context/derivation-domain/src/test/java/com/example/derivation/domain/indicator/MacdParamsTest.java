package com.example.derivation.domain.indicator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MacdParamsTest {

    @Test
    @DisplayName("SHORT - fast 5, slow 13, signal 5")
    void short_params() {
        assertThat(MacdParams.SHORT.fast()).isEqualTo(5);
        assertThat(MacdParams.SHORT.slow()).isEqualTo(13);
        assertThat(MacdParams.SHORT.signal()).isEqualTo(5);
    }

    @Test
    @DisplayName("STANDARD - fast 12, slow 26, signal 9")
    void standard_params() {
        assertThat(MacdParams.STANDARD.fast()).isEqualTo(12);
        assertThat(MacdParams.STANDARD.slow()).isEqualTo(26);
        assertThat(MacdParams.STANDARD.signal()).isEqualTo(9);
    }

    @Test
    @DisplayName("LONG - fast 19, slow 39, signal 9")
    void long_params() {
        assertThat(MacdParams.LONG.fast()).isEqualTo(19);
        assertThat(MacdParams.LONG.slow()).isEqualTo(39);
        assertThat(MacdParams.LONG.signal()).isEqualTo(9);
    }
}
