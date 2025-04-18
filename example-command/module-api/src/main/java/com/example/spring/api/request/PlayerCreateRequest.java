package com.example.spring.api.request;

import com.example.spring.domain.command.dto.PlayerCreateCommand;
import com.example.spring.domain.command.model.FullName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record PlayerCreateRequest(
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
        String firstName,
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
        String lastName
) {
    public PlayerCreateCommand toCommand() {
        return new PlayerCreateCommand(new FullName(firstName, lastName));
    }
}
