package com.example.spring.boot.modules.team.domain.repository.query;

import java.time.LocalDateTime;

public record TeamListQuery(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
}
