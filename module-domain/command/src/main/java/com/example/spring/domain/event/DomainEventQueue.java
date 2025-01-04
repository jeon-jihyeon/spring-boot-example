package com.example.spring.domain.event;

public enum DomainEventQueue {
    COMMAND_TEAM("team"),
    COMMAND_PLAYER("player"),
    DELETE_TEAM("delete-team");

    private final String name;

    DomainEventQueue(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
