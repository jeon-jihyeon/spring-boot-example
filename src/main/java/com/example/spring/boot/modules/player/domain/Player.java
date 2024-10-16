package com.example.spring.boot.modules.player.domain;

public class Player {
    private final Long id;
    private final Grade grade;
    private final FullName fullName;
    private final Long teamId;

    private Player(Long id, Grade grade, FullName fullName, Long teamId) {
        this.id = id;
        this.grade = grade;
        this.fullName = fullName;
        this.teamId = teamId;
    }
}
