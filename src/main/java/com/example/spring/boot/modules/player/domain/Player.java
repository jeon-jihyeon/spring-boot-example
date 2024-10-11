package com.example.spring.boot.modules.player.domain;

public class Player {
    private final Long id;
    private final Grade grade;
    private final FullName fullName;

    private Player(Long id, Grade grade, FullName fullName) {
        this.id = id;
        this.grade = grade;
        this.fullName = fullName;
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

    public static PlayerBuilder builder() {
        return new PlayerBuilder();
    }

    public static class PlayerBuilder {
        private Long id;
        private Grade grade;
        private FullName fullName;

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

        public Player build() {
            return new Player(this.id, this.grade, this.fullName);
        }

        public String toString() {
            return "Player.PlayerBuilder(id=" + this.id + ", grade=" + this.grade + ", fullName=" + this.fullName + ")";
        }
    }
}
