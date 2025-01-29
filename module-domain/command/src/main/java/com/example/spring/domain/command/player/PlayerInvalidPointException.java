package com.example.spring.domain.command.player;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class PlayerInvalidPointException extends BaseException {
    public PlayerInvalidPointException() {
        super(ErrorCode.INVALID_VALUE, "유효하지 않은 포인트 입니다.");
    }
}
