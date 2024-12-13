package com.example.spring.application.api.team;

import com.example.spring.application.api.team.data.TeamListResponse;
import com.example.spring.application.api.team.data.TeamParam;
import com.example.spring.domain.team.repository.TeamQueryRepository;
import com.example.spring.application.api.core.response.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamListController {
    private final TeamQueryRepository repository;

    public TeamListController(TeamQueryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/teams")
    public ResponseModel<List<TeamListResponse>> findTeams(final TeamParam param) {
        return ResponseModel.ok(repository.findTeams(param.toCondition()).stream().map(TeamListResponse::from).toList());
    }
}
