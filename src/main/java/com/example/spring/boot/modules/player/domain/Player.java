package com.example.spring.boot.modules.player.domain;

public class Player {
    private final Long id;
    private final Grade grade;
    private final FullName fullName;
    private final Team team;

    private Player(Long id, Grade grade, FullName fullName, Team team) {
        this.id = id;
        this.grade = grade;
        this.fullName = fullName;
        this.team = team;
    }

    public Long getId() {
        return id;
    }
    public Grade getGrade() {
        return grade;
    }
    public FullName getFullName() {
        return fullName;
    }
    public Team getTeam() {
        return team;
    }

    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private Long id;
        private Grade grade;
        private FullName fullName;
        private Team team;

        PlayerBuilder() {
        }

        public PlayerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder grade(Grade grade) {
            this.grade = grade;
            return this;
        }

        public PlayerBuilder fullName(FullName fullName) {
            this.fullName = fullName;
            return this;
        }

        public PlayerBuilder team(Team team) {
            this.team = team;
            return this;
        }

        public Player build() {
            return new Player(this.id, this.grade, this.fullName, this.team);
        }
    }
}
