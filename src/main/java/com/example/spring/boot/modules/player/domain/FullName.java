package com.example.spring.boot.modules.player.domain;

public record FullName(String firstName, String lastName) {
    public FullName {
        if (firstName == null || firstName.length() > 50) {
            throw new RuntimeException();
        }
        if (lastName == null || lastName.length() > 50) {
            throw new RuntimeException();
        }
    }
}
