package com.example.spring.domain.player.repository.condition;

import com.example.spring.domain.player.Grade;

public record PlayerCondition(Grade grade, String firstName, String lastName, Long teamId) {
}
