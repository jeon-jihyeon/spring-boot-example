package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.player.api.param.PlayerParam;
import com.example.spring.boot.modules.player.api.response.PlayerListResponse;
import com.example.spring.boot.modules.player.domain.repository.PlayerQueryRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerListController {
    private final PlayerQueryRepository repository;

    public PlayerListController(PlayerQueryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/players")
    public ResponseModel<List<PlayerListResponse>> findPlayers(final @ModelAttribute PlayerParam param) {
        return ResponseModel.ok(repository.findPlayers(param.toCondition()).stream().map(PlayerListResponse::from).toList());
    }
}
