package com.example.core.exception;

public class InvalidValueException extends BaseException {
    public InvalidValueException(String message) {
        super(message, LogLevel.WARN);
    }
}
