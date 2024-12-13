package com.example.spring.application.api.player;

import com.example.spring.application.api.core.response.ResponseModel;
import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerCreateResponse;
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
    public ResponseModel<PlayerCreateResponse> create(final @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerCreateResponse.from(service.invoke(data.toCommand())));
    }
}
