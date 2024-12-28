package com.example.spring.domain.player.dto;

import com.example.spring.domain.player.model.FullName;

public record PlayerCreateCommand(FullName fullName) {
}
