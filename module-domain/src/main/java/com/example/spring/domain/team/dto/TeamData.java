package com.example.spring.domain.team.dto;

import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.team.Team;

import java.time.LocalDateTime;
import java.util.List;

public record TeamData(Long id, String name, LocalDateTime startsAt, List<PlayerId> playerIds) {
    public static TeamData from(Team domain) {
        return new TeamData(domain.getId().value(), domain.getName().value(), domain.getStartsAt(), domain.getPlayerIds());
    }
}
