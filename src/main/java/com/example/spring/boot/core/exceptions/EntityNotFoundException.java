package com.example.spring.boot.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException() {
        super("The entity does not exist.");
    }

    public EntityNotFoundException(String name) {
        super(String.format("The %s does not exist.", name));
    }
}
