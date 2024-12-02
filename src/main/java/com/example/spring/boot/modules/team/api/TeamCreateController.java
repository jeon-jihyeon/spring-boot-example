package com.example.spring.boot.modules.team.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.team.api.data.TeamCreateRequest;
import com.example.spring.boot.modules.team.api.data.TeamResponse;
import com.example.spring.boot.modules.team.domain.TeamCreateService;
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
