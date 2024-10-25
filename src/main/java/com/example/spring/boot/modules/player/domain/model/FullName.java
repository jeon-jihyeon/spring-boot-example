package com.example.spring.boot.modules.player.domain.model;

import com.example.spring.boot.core.exceptions.InvalidValueException;
import org.springframework.util.StringUtils;

public record FullName(String firstName, String lastName) {
    public FullName {
        if (!StringUtils.hasText(firstName) || firstName.length() > 30) throw new InvalidValueException();
        if (!StringUtils.hasText(lastName) || lastName.length() > 30) throw new InvalidValueException();
    }
}
