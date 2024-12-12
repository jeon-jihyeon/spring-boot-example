package com.example.spring.domain.player.repository.query;

import com.example.spring.domain.player.Grade;

public record PlayerListQuery(Long id, Grade grade, String firstName, String lastName, Long teamId) {
}
