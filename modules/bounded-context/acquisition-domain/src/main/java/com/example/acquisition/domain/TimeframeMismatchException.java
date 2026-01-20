package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.exception.BaseException;
import com.example.core.exception.LogLevel;

public class TimeframeMismatchException extends BaseException {
    private static final String MESSAGE_TEMPLATE = "Timeframe mismatch: expected %s, but was %s";

    public TimeframeMismatchException(Timeframe expected, Timeframe actual) {
        super(MESSAGE_TEMPLATE.formatted(expected, actual), LogLevel.ERROR);
    }
}
