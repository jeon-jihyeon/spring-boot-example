package com.example.spring.application.api.player;

import com.example.spring.application.api.player.data.PlayerCreateRequest;
import com.example.spring.application.api.player.data.PlayerJoinRequest;
import com.example.spring.application.api.player.data.PlayerResponse;
import com.example.spring.application.common.ResponseModel;
import com.example.spring.domain.player.PlayerCommandJoinService;
import com.example.spring.domain.player.PlayerCommandLeaveService;
import com.example.spring.domain.player.PlayerCommandService;
import com.example.spring.domain.player.dto.PlayerLeaveCommand;
import com.example.spring.domain.player.model.PlayerId;
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
    public ResponseModel<PlayerResponse> create(final @RequestBody PlayerCreateRequest data) {
        return ResponseModel.ok(PlayerResponse.from(service.create(data.toCommand())));
    }

    @GetMapping("/api/players/{id}")
    public ResponseModel<PlayerResponse> getPlayer(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerResponse.from(service.read(new PlayerId(id))));
    }

    @PatchMapping("/api/players/{id}/teams")
    public ResponseModel<PlayerResponse> joinTeam(final @PathVariable Long id, final PlayerJoinRequest data) {
        return ResponseModel.ok(PlayerResponse.from(joinService.invoke(data.toCommand(id))));
    }

    @DeleteMapping("/api/players/{id}/teams")
    public ResponseModel<PlayerResponse> leaveTeam(final @PathVariable Long id) {
        return ResponseModel.ok(PlayerResponse.from(leaveService.invoke(new PlayerLeaveCommand(new PlayerId(id)))));
    }
}
