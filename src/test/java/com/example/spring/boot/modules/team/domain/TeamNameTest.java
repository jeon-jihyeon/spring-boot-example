package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.core.exceptions.InvalidValueException;
import com.example.spring.boot.modules.team.domain.model.TeamName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class TeamNameTest {

    @Test
    void test_validation() {
        assertThrows(InvalidValueException.class, () -> new TeamName("1234567890123456789012345678901"));
        assertThrows(InvalidValueException.class, () -> new TeamName(null));
    }
}