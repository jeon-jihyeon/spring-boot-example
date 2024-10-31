package com.example.spring.boot.modules.player.domain.condition;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.Grade;

public record PlayerCondition(
        Grade grade,
        FullName fullName,
        Long teamId
) {
}
