package com.example.spring.boot.modules.team.domain.event;

import java.util.List;

public record TeamCreateEvent(Long teamId, List<Long> playerIds) {
}
