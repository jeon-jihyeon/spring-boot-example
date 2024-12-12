package org.example.spring.application.api.player;

import org.example.spring.application.api.common.ResponseModel;
import org.example.spring.application.api.player.data.PlayerCreateRequest;
import org.example.spring.application.api.player.data.PlayerResponse;
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
