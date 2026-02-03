package com.example.derivation.domain.indicator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MacdParamsTest {

    @Test
    @DisplayName("STANDARD - fast 12, slow 26, signal 9")
    void standard_params() {
        assertThat(MacdParams.STANDARD.fast()).isEqualTo(12);
        assertThat(MacdParams.STANDARD.slow()).isEqualTo(26);
        assertThat(MacdParams.STANDARD.signal()).isEqualTo(9);
    }

    @Test
    @DisplayName("커스텀 파라미터 생성")
    void custom_params() {
        var params = new MacdParams(5, 13, 5);
        assertThat(params.fast()).isEqualTo(5);
        assertThat(params.slow()).isEqualTo(13);
        assertThat(params.signal()).isEqualTo(5);
    }
}
