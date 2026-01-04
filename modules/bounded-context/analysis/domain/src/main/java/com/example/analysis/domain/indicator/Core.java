package com.example.analysis.domain.indicator;

import com.example.core.enums.Timeframe;
import com.example.core.values.EpochMillis;
import com.example.core.values.Symbol;

public record Core(Symbol symbol, Timeframe timeframe, EpochMillis timestamp) {
}
