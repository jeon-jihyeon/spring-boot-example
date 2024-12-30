package com.example.spring.application.api.team.data;

import com.example.spring.application.common.InvalidValueException;
import com.example.spring.domain.team.dto.TeamCreateCommand;
import com.example.spring.domain.team.model.TeamName;
import org.springframework.util.StringUtils;

public record TeamCreateRequest(String name) {
    public TeamCreateRequest {
        if (!StringUtils.hasText(name) || name.length() > 30) throw new InvalidValueException();
    }

    public TeamCreateCommand toCommand() {
        return new TeamCreateCommand(new TeamName(name));
    }
}
