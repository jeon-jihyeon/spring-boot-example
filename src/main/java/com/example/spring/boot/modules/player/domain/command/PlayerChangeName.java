package com.example.spring.boot.modules.player.domain.command;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public record PlayerChangeName(PlayerId id, FullName fullName) {
}
