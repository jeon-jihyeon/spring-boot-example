package com.example.spring.domain;

public enum ErrorCode {
    NOT_FOUND("The entity does not exist."),
    INTERNAL_SERVER("An internal error occurred."),
    INVALID_VALUE("The data contains an invalid value.");

    private final String defaultMessage;

    ErrorCode(String defaultMessage) {
        this.defaultMessage = defaultMessage;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
