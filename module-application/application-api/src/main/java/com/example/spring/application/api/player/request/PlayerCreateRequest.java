package com.example.spring.application.api.player.request;

import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.model.FullName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PlayerCreateRequest(
        @NotEmpty
        @Max(value = 30)
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
        String firstName,
        @NotEmpty
        @Max(value = 30)
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
        String lastName
) {
    public PlayerCreateCommand toCommand() {
        return new PlayerCreateCommand(new FullName(firstName, lastName));
    }
}
