package com.example.spring.domain.player.command;

import com.example.spring.domain.player.FullName;

public record PlayerCreateCommand(FullName fullName) {
}