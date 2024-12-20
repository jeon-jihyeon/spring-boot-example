package com.example.spring.application.api.team;

import com.example.spring.application.api.team.data.TeamCreateRequest;
import com.example.spring.application.api.team.data.TeamResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.team.TeamId;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamController {
    private final TeamCreateService createService;
    private final TeamDetailService detailService;

    public TeamController(TeamCreateService createService, TeamDetailService detailService) {
        this.createService = createService;
        this.detailService = detailService;
    }

    @PostMapping("/api/teams")
    public ResponseModel<TeamResponse> create(final @RequestBody TeamCreateRequest data) {
        return ResponseModel.ok(TeamResponse.from(createService.invoke(data.toCommand())));
    }

    @GetMapping("/api/teams/{id}")
    public ResponseModel<TeamResponse> getTeam(final @PathVariable Long id) {
        return ResponseModel.ok(TeamResponse.from(detailService.invoke(new TeamId(id))));
    }
}
