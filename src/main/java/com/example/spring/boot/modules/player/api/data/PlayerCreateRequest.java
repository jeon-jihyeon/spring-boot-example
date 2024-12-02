package com.example.spring.boot.modules.player.api.data;

import com.example.spring.boot.modules.player.domain.model.FullName;
import com.example.spring.boot.modules.player.domain.repository.command.PlayerCreateCommand;

public record PlayerCreateRequest(String firstName, String lastName) {
    public PlayerCreateCommand toCommand() {
        return new PlayerCreateCommand(new FullName(firstName, lastName));
    }
}
