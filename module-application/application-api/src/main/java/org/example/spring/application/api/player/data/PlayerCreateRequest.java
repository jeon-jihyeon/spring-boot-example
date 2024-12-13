package org.example.spring.application.api.player.data;

import com.example.spring.domain.player.FullName;
import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import org.example.spring.application.api.core.exception.InvalidValueException;
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
