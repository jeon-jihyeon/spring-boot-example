package com.example.spring.boot.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidValueException extends RuntimeException {

    public InvalidValueException() {
        super("Invalid value.");
    }

    public InvalidValueException(String message) {
        super(message);
    }
}