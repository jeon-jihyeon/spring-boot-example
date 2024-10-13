package com.example.spring.boot.modules.team.domain;

import java.time.LocalDateTime;

public record TeamCondition(
        String name,
        LocalDateTime startsAt
) {
}
