package com.example.spring.api.player.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerAddPointRequestTest {
    @Test
    void shouldInitialize() {
        // given
        var request = new PlayerAddPointRequest(100);

        // when
        Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
        assertThat(request.point()).isEqualTo(100);
    }

    @Test
    void shouldThrowExceptionWhenInitializedWithInvalidValue() {
        // given
        var request = new PlayerAddPointRequest(null);

        // when
        Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();

        var properties = violations.stream().map(ConstraintViolation::getPropertyPath).map(Path::toString).toList();
        assertThat(properties.contains("point")).isTrue();
    }
}