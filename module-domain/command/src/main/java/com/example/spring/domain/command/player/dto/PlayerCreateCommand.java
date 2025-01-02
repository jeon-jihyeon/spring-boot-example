package com.example.spring.domain.command.player.dto;

import com.example.spring.domain.command.player.model.FullName;

public record PlayerCreateCommand(FullName fullName) {
}
