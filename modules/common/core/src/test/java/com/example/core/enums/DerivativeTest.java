package com.example.core.enums;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DerivativeTest {

    @Test
    @DisplayName("모든 Derivative 값 존재 확인")
    void values_containsAllExpectedValues() {
        assertThat(Derivative.values())
                .containsExactly(
                        Derivative.FUTURES,
                        Derivative.OPTIONS,
                        Derivative.SWAP,
                        Derivative.FORWARD,
                        Derivative.PERPETUAL
                );
    }

    @Test
    @DisplayName("valueOf - 문자열로 Derivative 조회")
    void valueOf_validString_returnsDerivative() {
        assertThat(Derivative.valueOf("FUTURES")).isEqualTo(Derivative.FUTURES);
        assertThat(Derivative.valueOf("OPTIONS")).isEqualTo(Derivative.OPTIONS);
        assertThat(Derivative.valueOf("SWAP")).isEqualTo(Derivative.SWAP);
        assertThat(Derivative.valueOf("FORWARD")).isEqualTo(Derivative.FORWARD);
        assertThat(Derivative.valueOf("PERPETUAL")).isEqualTo(Derivative.PERPETUAL);
    }
}
