package com.example.acquisition.domain;

import com.example.core.enums.Timeframe;
import com.example.core.exception.BaseException;
import com.example.core.exception.LogLevel;

public class TimeframeHierarchyException extends BaseException {
    private static final String MESSAGE_TEMPLATE = "Timeframe hierarchy violation: expected a smaller timeframe than %s, but was %s";

    public TimeframeHierarchyException(Timeframe expected, Timeframe actual) {
        super(MESSAGE_TEMPLATE.formatted(expected, actual), LogLevel.ERROR);
    }
}
