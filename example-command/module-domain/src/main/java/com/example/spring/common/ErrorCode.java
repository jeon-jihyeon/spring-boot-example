package com.example.spring.common;

public enum ErrorCode {
    INVALID_VALUE(400, LogLevel.WARN, "The data contains an invalid value."),
    NOT_FOUND(404, LogLevel.WARN, "The entity does not exist."),
    ALREADY_EXiST(409, LogLevel.WARN, "The entity already exists,."),
    INTERNAL_SERVER_ERROR(500, LogLevel.ERROR, "An internal error occurred.");
    private final int status;
    private final LogLevel logLevel;
    private final String defaultMessage;

    ErrorCode(int status, LogLevel logLevel, String defaultMessage) {
        this.status = status;
        this.logLevel = logLevel;
        this.defaultMessage = defaultMessage;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public int getStatus() {
        return status;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
