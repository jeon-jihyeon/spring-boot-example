package com.example.derivation.domain.indicator;

public record MacdParams(int fast, int slow, int signal) {
    public static final MacdParams STANDARD = new MacdParams(12, 26, 9);
}
