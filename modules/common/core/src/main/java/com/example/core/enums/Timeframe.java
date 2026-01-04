package com.example.core.enums;

public enum Timeframe {
    MINUTES(60),
    HOURS(3600),
    DAYS(86400),
    WEEKS(604800);

    private final int seconds;

    Timeframe(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
}
