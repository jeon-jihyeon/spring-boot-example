package com.example.spring.application.api.team.request;

import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TeamCreateRequestTest {
    @Test
    void shouldInitialize() {
        // given
        var request = new TeamCreateRequest("abcd1234");

        // when
        var factory = Validation.buildDefaultValidatorFactory();
        var validator = factory.getValidator();
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
        assertThat(request.name()).isEqualTo("abcd1234");
    }
}