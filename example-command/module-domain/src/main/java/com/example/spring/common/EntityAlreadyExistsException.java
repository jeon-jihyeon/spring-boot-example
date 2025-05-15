package com.example.spring.common;

public class EntityAlreadyExistsException extends BaseException {
    public EntityAlreadyExistsException(Object data) {
        super(ErrorCode.ALREADY_EXiST, data);
    }
}