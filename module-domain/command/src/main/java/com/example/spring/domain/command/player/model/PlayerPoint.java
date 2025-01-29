package com.example.spring.domain.command.player.model;

public record PlayerPoint(Integer value) {
    public static final PlayerPoint ZERO = new PlayerPoint(0);
}
