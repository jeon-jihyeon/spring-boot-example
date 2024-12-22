package com.example.spring.application.api.player;

import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.player.PlayerId;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerCommandController {
    private final PlayerCreateService createService;
    private final PlayerDetailService detailService;

    public PlayerCommandController(PlayerCreateService createService, PlayerDetailService detailService) {
        this.createService = createService;
        this.detailService = detailService;
    }

    @PostMapping("/api/players")
    public ResponseModel<PlayerResponse> create(final @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerResponse.from(createService.invoke(data.toCommand())));
    }

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerResponse> getPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerResponse.from(detailService.invoke(new PlayerId(id))));
    }
}
