package com.example.spring.common;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }

    public EntityNotFoundException(Object data) {
        super(ErrorCode.NOT_FOUND, data);
    }
}
