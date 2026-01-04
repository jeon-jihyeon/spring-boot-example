package com.example.analysis.domain.indicator;

public enum EmaParams {
    SHORT(9),
    STANDARD(12),
    LONG(26);

    private final int period;

    EmaParams(int period) {
        this.period = period;
    }

    public int period() {
        return period;
    }
}
