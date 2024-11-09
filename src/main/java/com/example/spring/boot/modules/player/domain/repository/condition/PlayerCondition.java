package com.example.spring.boot.modules.player.domain.repository.condition;

import com.example.spring.boot.modules.player.domain.model.Grade;

public record PlayerCondition(Grade grade, String firstName, String lastName, Long teamId) {
}
