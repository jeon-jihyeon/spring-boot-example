package org.example.spring.application.api.player;

import com.example.spring.domain.player.repository.PlayerQueryRepository;
import org.example.spring.application.api.common.ResponseModel;
import org.example.spring.application.api.player.data.PlayerListResponse;
import org.example.spring.application.api.player.data.PlayerParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerListController {
    private final PlayerQueryRepository repository;

    public PlayerListController(PlayerQueryRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/players")
    public ResponseModel<List<PlayerListResponse>> findPlayers(final PlayerParam param) {
        return ResponseModel.ok(repository.findPlayers(param.toCondition()).stream().map(PlayerListResponse::from).toList());
    }
}
