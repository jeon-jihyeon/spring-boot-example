package com.example.spring.application.api.player;

import com.example.spring.application.api.core.response.ResponseModel;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
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
