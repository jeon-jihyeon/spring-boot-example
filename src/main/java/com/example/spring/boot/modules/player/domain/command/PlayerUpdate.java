package com.example.spring.boot.modules.player.domain.command;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.model.PlayerId;

public record PlayerUpdate(PlayerId id, FullName fullName) {
}
