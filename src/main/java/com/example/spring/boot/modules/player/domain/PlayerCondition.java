package com.example.spring.boot.modules.player.domain;

public record PlayerCondition(
        Grade grade,
        FullName fullName,
        Long teamId
) {
}
