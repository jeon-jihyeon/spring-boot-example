package com.example.spring.boot.modules.team.api;

import com.example.spring.boot.modules.team.domain.TeamDeleteService;
import com.example.spring.boot.modules.team.domain.model.TeamId;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamDeleteController {
    private final TeamDeleteService service;

    public TeamDeleteController(TeamDeleteService service) {
        this.service = service;
    }

    @DeleteMapping("/api/teams/{id}")
    public void delete(final @PathVariable Long id) {
        service.invoke(new TeamId(id));
    }
}
