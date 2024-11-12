package com.example.spring.boot.modules.player.domain.repository.query;

import com.example.spring.boot.modules.player.domain.model.Grade;

public record PlayerListQuery(Long id, Grade grade, String firstName, String lastName, Long teamId) {
}
