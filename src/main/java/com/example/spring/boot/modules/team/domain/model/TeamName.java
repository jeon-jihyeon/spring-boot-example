package com.example.spring.boot.modules.team.domain.model;

import com.example.spring.boot.core.exception.InvalidValueException;
import org.springframework.util.StringUtils;

public record TeamName(String value) {
    public TeamName {
        if (!StringUtils.hasText(value) || value.length() > 30) throw new InvalidValueException();
    }
}
