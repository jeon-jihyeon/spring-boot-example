package com.example.derivation.infra;

import com.example.derivation.domain.calculator.EmaCalculator;
import com.example.derivation.domain.calculator.MacdCalculator;
import com.example.derivation.domain.indicator.EmaParams;
import com.example.derivation.domain.indicator.MacdParams;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CalculatorConfig {

    @Bean
    public EmaCalculator emaCalculator() {
        return new EmaCalculator(EmaParams.STANDARD);
    }

    @Bean
    public MacdCalculator macdCalculator() {
        return new MacdCalculator(MacdParams.STANDARD);
    }
}
