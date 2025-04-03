package com.example.spring.domain.command.model;

public record TeamId(Long value) {
    public static final TeamId NoTeam = new TeamId(0L);
}
