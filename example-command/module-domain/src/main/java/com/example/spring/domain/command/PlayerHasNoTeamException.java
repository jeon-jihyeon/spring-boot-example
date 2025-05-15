package com.example.spring.domain.command;

import com.example.spring.common.BaseException;
import com.example.spring.common.ErrorCode;

public class PlayerHasNoTeamException extends BaseException {
    public PlayerHasNoTeamException() {
        super(ErrorCode.INVALID_VALUE, "플레이어의 팀이 존재하지 않습니다.");
    }
}
