package com.example.spring.application.api.team.request;

import com.example.spring.domain.command.team.dto.TeamCreateCommand;
import com.example.spring.domain.command.team.model.TeamName;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public record TeamCreateRequest(
        @NotEmpty
        @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$")
        String name
) {
    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name));
    }
}
