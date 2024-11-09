package com.example.spring.boot.modules.team.domain.repository.condition;

import java.time.LocalDateTime;

public record TeamCondition(String name, LocalDateTime startsAt) {
}
