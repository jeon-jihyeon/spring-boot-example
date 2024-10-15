package com.example.spring.boot.modules.team.domain;

import com.example.spring.boot.modules.player.domain.Player;

import java.time.LocalDateTime;
import java.util.List;

public class Team {
    private final Long id;

    private final TeamName name;

    private final LocalDateTime startsAt;

    private final List<Player> players;

    private Team(Long id, TeamName name, LocalDateTime startsAt, List<Player> players) {
        this.id = id;
        this.name = name;
        this.startsAt = startsAt;
        this.players = players;
    }

    public static GameBuilder builder() {
        return new GameBuilder();
    }

    public Long getId() {
        return id;
    }

    public TeamName getName() {
        return name;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public static class GameBuilder {
        private Long id;
        private TeamName name;
        private LocalDateTime startsAt;
        private List<Player> players;

        GameBuilder() {
        }

        public GameBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public GameBuilder name(TeamName name) {
            this.name = name;
            return this;
        }

        public GameBuilder startsAt(LocalDateTime startsAt) {
            this.startsAt = startsAt;
            return this;
        }

        public GameBuilder players(List<Player> players) {
            this.players = players;
            return this;
        }

        public Team build() {
            return new Team(this.id, this.name, this.startsAt, this.players);
        }
    }
}
