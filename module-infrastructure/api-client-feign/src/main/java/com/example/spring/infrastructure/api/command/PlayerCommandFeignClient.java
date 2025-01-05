package com.example.spring.infrastructure.api.command;

import com.example.spring.domain.command.player.PlayerCommandApiClient;
import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFeignClient implements PlayerCommandApiClient {
    private final CommandFeignApi api;

    public PlayerCommandFeignClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return api.findPlayer(id.value()).data().toData();
    }
}
