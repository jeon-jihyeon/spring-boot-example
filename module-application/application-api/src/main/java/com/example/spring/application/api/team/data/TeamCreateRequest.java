package com.example.spring.application.api.team.data;

import com.example.spring.domain.team.TeamName;
import com.example.spring.domain.team.repository.command.TeamCreateCommand;
import com.example.spring.application.api.core.exception.InvalidValueException;
import org.springframework.util.StringUtils;

import java.util.List;

public record TeamCreateRequest(String name, List<Long> playerIds) {
    public TeamCreateRequest {
        if (!StringUtils.hasText(name) || name.length() > 30) throw new InvalidValueException();
    }

    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name), playerIds);
    }
}
