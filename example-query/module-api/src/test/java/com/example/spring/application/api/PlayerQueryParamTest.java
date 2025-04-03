package com.example.spring.application.api;

import com.example.spring.api.PlayerQueryParam;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validation;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerQueryParamTest {
    @Test
    void shouldInitialize() {
        // given
        var request = new PlayerQueryParam(100L);

        // when
        jakarta.validation.Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
        assertThat(request.teamId()).isEqualTo(100L);
    }

    @Test
    void shouldThrowExceptionWhenInitializedWithInvalidValue() {
        // given
        var request = new PlayerQueryParam(null);

        // when
        jakarta.validation.Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();

        var properties = violations.stream().map(ConstraintViolation::getPropertyPath).map(Path::toString).toList();
        assertThat(properties.contains("teamId")).isTrue();
    }
}