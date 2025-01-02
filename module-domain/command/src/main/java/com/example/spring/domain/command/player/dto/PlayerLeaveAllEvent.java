package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.PlayerId;

import java.util.List;

public record PlayerLeaveAllEvent(List<PlayerId> playerIds) {
}
