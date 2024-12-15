package com.example.spring.domain.team.query;

import java.time.LocalDateTime;

public record TeamListQuery(Long id, String name, LocalDateTime startsAt, Integer playerCount) {
}