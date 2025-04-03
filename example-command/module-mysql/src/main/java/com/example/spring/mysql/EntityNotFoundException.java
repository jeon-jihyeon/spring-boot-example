package com.example.spring.mysql;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }
}
