package com.example.spring.boot.modules.team.domain;

import java.time.LocalDateTime;

public record TeamCondition(
        TeamName name,
        LocalDateTime startsAt
) {
}
