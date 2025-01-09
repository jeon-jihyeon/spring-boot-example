package com.example.spring.domain.command.player;

import com.example.spring.domain.BaseException;
import com.example.spring.domain.ErrorCode;

public class PlayerHasNoTeamException extends BaseException {
    public PlayerHasNoTeamException() {
        super(ErrorCode.INVALID_VALUE, null);
    }
}
