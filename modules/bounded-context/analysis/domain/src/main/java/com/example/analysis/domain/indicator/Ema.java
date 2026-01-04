package com.example.analysis.domain.indicator;

import java.math.BigDecimal;

public record Ema(Core core, BigDecimal value, int period) implements Indicator {
    @Override
    public Code code() {
        return Code.EMA;
    }
}