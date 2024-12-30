package com.example.spring.application.api.team;

import com.example.spring.domain.team.TeamCommandService;
import org.springframework.stereotype.Component;

@Component
public class TeamEventListener {
    private final TeamCommandService service;

    public TeamEventListener(TeamCommandService service) {
        this.service = service;
    }
}
