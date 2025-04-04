package com.example.spring.api;

import com.example.spring.common.ResponseModel;
import com.example.spring.domain.query.PlayerQueryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerQueryResponse> findPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerQueryResponse.from(service.query(id)));
    }
}
