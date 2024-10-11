package com.example.spring.boot.modules.player.domain;

import java.time.LocalDateTime;

public class Game {
    private final Long id;

    private final String name;

    private final LocalDateTime startsAt;

    private Game(Long id, String name, LocalDateTime startsAt) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public static class GameBuilder {
        private Long id;
        private String name;
        private LocalDateTime startsAt;

        GameBuilder() {
        }

        public GameBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public GameBuilder name(String name) {
            this.name = name;
            return this;
        }

        public GameBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public Game build() {
            return new Game(this.id, this.name, this.startsAt);
        }

        public String toString() {
            return "Game.GameBuilder(id=" + this.id + ", name=" + this.name + ", startsAt=" + this.startsAt + ")";
        }
    }
}
