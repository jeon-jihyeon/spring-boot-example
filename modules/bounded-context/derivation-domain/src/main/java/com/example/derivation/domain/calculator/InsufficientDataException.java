package com.example.derivation.domain.calculator;

import com.example.core.exception.BaseException;
import com.example.core.exception.LogLevel;

public class InsufficientDataException extends BaseException {
    public InsufficientDataException(String message) {
        super(message, LogLevel.WARN);
    }
}
