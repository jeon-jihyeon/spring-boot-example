package com.example.spring.boot.modules.player.api;

import com.example.spring.boot.core.response.ResponseModel;
import com.example.spring.boot.modules.player.api.data.PlayerCreateRequest;
import com.example.spring.boot.modules.player.api.data.PlayerResponse;
import com.example.spring.boot.modules.player.domain.PlayerCreateService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerCreateController {
    private final PlayerCreateService service;

    public PlayerCreateController(PlayerCreateService service) {
        this.service = service;
    }

    @PostMapping("/api/players")
    public ResponseModel<PlayerResponse> create(final @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerResponse.from(service.invoke(data.toCommand())));
    }
}
