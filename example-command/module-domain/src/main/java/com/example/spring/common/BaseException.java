package com.example.spring.common;

public abstract class BaseException extends RuntimeException {
    private final ErrorCode code;
    private final Object data;

    protected BaseException(String message, ErrorCode code, Object data) {
        super(message);
        this.code = code;
        this.data = data;
    }

    protected BaseException(ErrorCode code, Object data) {
        super(code.getDefaultMessage());
        this.code = code;
        this.data = data;
    }

    public ErrorCode getCode() {
        return code;
    }

    public Object getData() {
        return data;
    }
}
