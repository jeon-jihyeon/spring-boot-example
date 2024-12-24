package com.example.spring.infrastructure.db.event;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class EventNotFoundException extends BaseException {
    public EventNotFoundException() {
        super(ErrorCode.NOT_FOUND, null);
    }
}
