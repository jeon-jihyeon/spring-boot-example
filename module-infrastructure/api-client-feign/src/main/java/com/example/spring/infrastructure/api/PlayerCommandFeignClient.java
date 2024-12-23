package com.example.spring.infrastructure.api;

import com.example.spring.domain.player.PlayerCommandApiClient;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.dto.PlayerData;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFeignClient implements PlayerCommandApiClient {
    private final CommandFeignApi api;

    public PlayerCommandFeignClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return api.getPlayer(id.value()).data().toData();
    }
}
