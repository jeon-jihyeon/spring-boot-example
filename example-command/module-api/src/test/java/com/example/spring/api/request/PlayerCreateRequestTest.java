package com.example.spring.api.request;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerCreateRequestTest {
    @Test
    void shouldInitialize() {
        // given
        var request = new PlayerCreateRequest("abcd1234", "abcd1234");

        // when
        Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isEmpty();
        assertThat(request.firstName()).isEqualTo("abcd1234");
        assertThat(request.lastName()).isEqualTo("abcd1234");
    }

    @Test
    void shouldThrowExceptionWhenInitializedWithInvalidValue() {
        // given
        var request = new PlayerCreateRequest("abcd567", "abcd567");

        // when
        Validator validator;
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        var violations = validator.validate(request);

        // then
        assertThat(violations).isNotEmpty();

        var properties = violations.stream().map(ConstraintViolation::getPropertyPath).map(Path::toString).toList();
        assertThat(properties.contains("firstName")).isTrue();
        assertThat(properties.contains("lastName")).isTrue();
    }
}