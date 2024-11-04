package com.example.spring.boot.modules.player.domain.repository.command;

import com.example.spring.boot.modules.player.domain.model.FullName;

public record PlayerCreateCommand(FullName fullName) {
}
