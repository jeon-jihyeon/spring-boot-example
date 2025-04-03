package com.example.spring.domain.event;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class EventAlreadyExistsException extends BaseException {
    public EventAlreadyExistsException() {
        super(ErrorCode.ALREADY_EXiST, null);
    }
}
