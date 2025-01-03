package com.example.spring.application.api.player.data;

import com.example.spring.application.common.InvalidValueException;
import com.example.spring.domain.command.player.dto.PlayerCreateCommand;
import com.example.spring.domain.command.player.model.FullName;
import org.springframework.util.StringUtils;

public record PlayerCreateRequest(String firstName, String lastName) {

    public PlayerCreateRequest {
        if (!StringUtils.hasText(firstName) || firstName.length() > 30) throw new InvalidValueException();
        if (!StringUtils.hasText(lastName) || lastName.length() > 30) throw new InvalidValueException();
    }

    public PlayerCreateCommand toCommand() {
        return new PlayerCreateCommand(new FullName(firstName, lastName));
    }
}
