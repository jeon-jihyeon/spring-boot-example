package com.example.spring.application.api.team;

import com.example.spring.application.api.team.request.TeamQueryParam;
import com.example.spring.application.api.team.response.TeamQueryResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.query.team.TeamQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamQueryController {
    private final TeamQueryService service;

    public TeamQueryController(TeamQueryService service) {
        this.service = service;
    }

    @GetMapping("/api/teams")
    public ResponseModel<List<TeamQueryResponse>> findTeams(final TeamQueryParam param) {
        return ResponseModel.ok(service.findTeams(param.toCondition()).stream().map(TeamQueryResponse::from).toList());
    }
}
