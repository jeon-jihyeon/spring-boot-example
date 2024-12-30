package com.example.spring.domain.team.model;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class EmptyPlayerIdsException extends BaseException {
    public EmptyPlayerIdsException() {
        super(ErrorCode.INVALID_VALUE, null);
    }
}
