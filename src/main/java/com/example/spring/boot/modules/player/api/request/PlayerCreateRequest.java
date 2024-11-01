package com.example.spring.boot.modules.player.api.request;

import com.example.spring.boot.modules.player.domain.command.PlayerCreateCommand;
import com.example.spring.boot.modules.player.domain.model.FullName;

public record PlayerCreateRequest(String firstName, String lastName) {
    public PlayerCreateCommand toCommand() {
        return new PlayerCreateCommand(new FullName(firstName, lastName));
    }
}
