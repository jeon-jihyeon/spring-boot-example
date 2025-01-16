package com.example.spring.application.api.player;

import com.example.spring.application.api.player.request.PlayerCreateRequest;
import com.example.spring.application.api.player.request.PlayerJoinRequest;
import com.example.spring.application.api.player.response.PlayerCommandResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.command.player.PlayerCommandJoinService;
import com.example.spring.domain.command.player.PlayerCommandLeaveService;
import com.example.spring.domain.command.player.PlayerCommandService;
import com.example.spring.domain.command.player.dto.PlayerLeaveCommand;
import com.example.spring.domain.command.player.model.PlayerId;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
public class PlayerCommandController {
    private final PlayerCommandService service;
    private final PlayerCommandJoinService joinService;
    private final PlayerCommandLeaveService leaveService;

    public PlayerCommandController(PlayerCommandService service, PlayerCommandJoinService joinService, PlayerCommandLeaveService leaveService) {
        this.service = service;
        this.joinService = joinService;
        this.leaveService = leaveService;
    }

    @PostMapping("/api/players")
    public ResponseModel<PlayerCommandResponse> create(final @Valid @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerCommandResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerCommandResponse> findPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerCommandResponse.from(service.read(new PlayerId(id))));
    }

    @PatchMapping("/api/players/{id}/teams")
    public ResponseModel<PlayerCommandResponse> joinTeam(final @PathVariable Long id, final @Valid @RequestBody PlayerJoinRequest data) {
        return ResponseModel.ok(PlayerCommandResponse.from(joinService.invoke(data.toCommand(id))));
    }

    @DeleteMapping("/api/players/{id}/teams")
    public ResponseModel<PlayerCommandResponse> leaveTeam(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerCommandResponse.from(leaveService.invoke(new PlayerLeaveCommand(new PlayerId(id)))));
    }
}
