package com.example.spring.application.api.team;

import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.team.TeamCommandService;
import com.example.spring.domain.team.model.TeamId;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamCommandController {
    private final TeamCommandService service;

    public TeamCommandController(TeamCommandService service) {
        this.service = service;
    }

    @PostMapping("/api/teams")
    public ResponseModel<TeamResponse> create(final @RequestBody TeamCreateRequest data) {
        return ResponseModel.ok(TeamResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/teams/{id}")
    public ResponseModel<TeamResponse> getTeam(final @PathVariable Long id) {
        return ResponseModel.ok(TeamResponse.from(service.read(new TeamId(id))));
    }
}
