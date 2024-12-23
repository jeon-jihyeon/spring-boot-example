package com.example.spring.application.api.player;

import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.player.PlayerCommandService;
import com.example.spring.domain.player.PlayerId;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerCommandController {
    private final PlayerCommandService service;

    public PlayerCommandController(PlayerCommandService service) {
        this.service = service;
    }

    @PostMapping("/api/players")
    public ResponseModel<PlayerResponse> create(final @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerResponse> getPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerResponse.from(service.read(new PlayerId(id))));
    }
}
