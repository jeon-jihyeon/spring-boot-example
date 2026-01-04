package com.example.analysis.domain.indicator;

import java.math.BigDecimal;

public record Macd(
        Core core,
        BigDecimal value,
        BigDecimal signal,
        BigDecimal histogram,
        MacdParams params
) implements Indicator {
    @Override
    public Code code() {
        return Code.MACD;
    }
}
