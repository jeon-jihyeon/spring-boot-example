package com.example.spring.application.api.player;

import com.example.spring.application.api.player.request.PlayerQueryParam;
import com.example.spring.application.api.player.response.PlayerQueryResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.query.player.PlayerQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerQueryController {
    private final PlayerQueryService service;

    public PlayerQueryController(PlayerQueryService service) {
        this.service = service;
    }

    @GetMapping("/api/players")
    public ResponseModel<List<PlayerQueryResponse>> findPlayers(final @Valid PlayerQueryParam param) {
        return ResponseModel.ok(service.findPlayers(param.toCondition()).stream().map(PlayerQueryResponse::from).toList());
    }
}
