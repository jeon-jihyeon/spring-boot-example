package com.example.spring.boot.modules.player.domain;

public class Player {
    private final Long id;
    private final Grade grade;
    private final FullName fullName;

    public Player(Long id, Grade grade, FullName fullName) {
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
}
