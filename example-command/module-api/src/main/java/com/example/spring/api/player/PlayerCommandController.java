package com.example.spring.api.player;

import com.example.spring.api.common.ResponseModel;
import com.example.spring.api.player.request.PlayerAddPointRequest;
import com.example.spring.api.player.request.PlayerCreateRequest;
import com.example.spring.api.player.response.PlayerCommandResponse;
import com.example.spring.domain.command.PlayerCommandService;
import com.example.spring.domain.command.model.PlayerId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerCommandController {
    private final PlayerCommandService service;

    public PlayerCommandController(PlayerCommandService service) {
        this.service = service;
    }

    @PostMapping("/api/players")
    public ResponseModel<PlayerCommandResponse> create(final @Valid @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerCommandResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerCommandResponse> findPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerCommandResponse.from(service.read(new PlayerId(id))));
    }

    @PatchMapping("/api/players/{id}")
    public ResponseModel<?> addPoint(final @PathVariable Long id, final @Valid @RequestBody PlayerAddPointRequest data) {
        service.addPoint(data.toCommand(id));
        return ResponseModel.ok();
    }

    @DeleteMapping("/api/players/{id}")
    public ResponseModel<?> deletePlayer(final @PathVariable Long id) {
        service.delete(new PlayerId(id));
        return ResponseModel.ok();
    }
}
