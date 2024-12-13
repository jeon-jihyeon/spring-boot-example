package com.example.spring.application.api.team;

import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.api.core.response.ResponseModel;
import com.example.spring.application.api.team.data.TeamCreateRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamCreateController {
    private final TeamCreateService service;

    public TeamCreateController(TeamCreateService service) {
        this.service = service;
    }

    @PostMapping("/api/teams")
    public ResponseModel<TeamResponse> create(final @RequestBody TeamCreateRequest data) {
        return ResponseModel.ok(TeamResponse.from(service.invoke(data.toCommand())));
    }
}
