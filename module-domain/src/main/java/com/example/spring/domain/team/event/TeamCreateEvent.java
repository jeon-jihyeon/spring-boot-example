package com.example.spring.domain.team.event;

import java.util.List;

public record TeamCreateEvent(Long teamId, List<Long> playerIds) {
}
