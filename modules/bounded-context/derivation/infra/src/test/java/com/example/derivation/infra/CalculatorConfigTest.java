package com.example.derivation.infra;

import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorConfigTest {

    private final CalculatorConfig config = new CalculatorConfig();

    @Test
    @DisplayName("emaCalculator bean 생성")
    void emaCalculator_createsBean() {
        EmaCalculator calculator = config.emaCalculator();

        assertThat(calculator).isNotNull();
    }

    @Test
    @DisplayName("macdCalculator bean 생성")
    void macdCalculator_createsBean() {
        MacdCalculator calculator = config.macdCalculator();

        assertThat(calculator).isNotNull();
    }
}
