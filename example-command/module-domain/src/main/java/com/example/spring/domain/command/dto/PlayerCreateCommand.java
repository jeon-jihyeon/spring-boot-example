package com.example.spring.domain.command.dto;

import com.example.spring.domain.command.model.FullName;

public record PlayerCreateCommand(FullName fullName) {
}
