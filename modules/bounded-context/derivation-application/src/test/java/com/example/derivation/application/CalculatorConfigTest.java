package com.example.derivation.application;

import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorConfigTest {

    private final CalculatorConfig config = new CalculatorConfig();

    @Test
    @DisplayName("emaCalculator 빈 생성")
    void emaCalculator_createsBean() {
        // when
        EmaCalculator calculator = config.emaCalculator();

        // then
        assertThat(calculator).isNotNull();
        assertThat(calculator).isInstanceOf(EmaCalculator.class);
    }

    @Test
    @DisplayName("macdCalculator 빈 생성")
    void macdCalculator_createsBean() {
        // when
        MacdCalculator calculator = config.macdCalculator();

        // then
        assertThat(calculator).isNotNull();
        assertThat(calculator).isInstanceOf(MacdCalculator.class);
    }
}
