package com.example.analysis.domain.indicator;

public enum MacdParams {
    SHORT(5, 13, 5),
    STANDARD(12, 26, 9),
    LONG(19, 39, 9);

    private final int fast;
    private final int slow;
    private final int signal;

    MacdParams(int fast, int slow, int signal) {
        this.fast = fast;
        this.slow = slow;
        this.signal = signal;
    }

    public int fast() {
        return fast;
    }

    public int slow() {
        return slow;
    }

    public int signal() {
        return signal;
    }
}
