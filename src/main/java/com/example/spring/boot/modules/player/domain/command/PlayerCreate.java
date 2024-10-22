package com.example.spring.boot.modules.player.domain.command;

import com.example.spring.boot.modules.player.domain.model.FullName;

public record PlayerCreate(FullName fullName) {
}
