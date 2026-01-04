package com.example.core.exception;

public abstract class BaseException extends RuntimeException {
    private final LogLevel logLevel;

    protected BaseException(String message, LogLevel logLevel) {
        super(message);
        this.logLevel = logLevel;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }
}
