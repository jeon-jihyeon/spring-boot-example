package com.example.spring.infrastructure.db;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class OutboxNotFoundException extends BaseException {
    public OutboxNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }
}
