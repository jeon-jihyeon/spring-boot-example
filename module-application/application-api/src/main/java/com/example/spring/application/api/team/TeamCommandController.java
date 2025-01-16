package com.example.spring.application.api.team;

import com.example.spring.application.api.team.request.TeamCreateRequest;
import com.example.spring.application.api.team.response.TeamCommandResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.team.TeamCommandService;
import com.example.spring.domain.command.team.dto.TeamDeleteCommand;
import com.example.spring.domain.command.team.model.TeamId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamCommandController {
    private final TeamCommandService service;

    public TeamCommandController(TeamCommandService service) {
        this.service = service;
    }

    @PostMapping("/api/teams")
    public ResponseModel<TeamCommandResponse> create(final @Valid @RequestBody TeamCreateRequest data) {
        return ResponseModel.ok(TeamCommandResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/teams/{id}")
    public ResponseModel<TeamCommandResponse> findTeam(final @PathVariable Long id) {
        return ResponseModel.ok(TeamCommandResponse.from(service.read(new TeamId(id))));
    }

    @DeleteMapping("/api/teams/{id}")
    public ResponseModel<?> deleteTeam(final @PathVariable Long id) {
        service.delete(new TeamDeleteCommand(new TeamId(id)));
        return ResponseModel.ok();
    }
}
