package com.example.spring.domain.command;

import com.example.spring.common.BaseException;
import com.example.spring.common.ErrorCode;

public class PlayerInvalidPointException extends BaseException {
    public PlayerInvalidPointException() {
        super(ErrorCode.INVALID_VALUE, "유효하지 않은 포인트 입니다.");
    }
}
