package com.example.spring.boot.modules.team.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.team.api.param.TeamParam;
import com.example.spring.boot.modules.team.api.response.TeamListResponse;
import com.example.spring.boot.modules.team.domain.repository.TeamQueryRepository;
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
