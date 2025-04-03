package com.example.spring.feign;

import com.example.spring.domain.query.PlayerCommandApiClient;
import com.example.spring.domain.query.PlayerQuery;
import org.springframework.stereotype.Component;

@Component
public class PlayerCommandFeignClient implements PlayerCommandApiClient {
    private final CommandFeignApi api;

    public PlayerCommandFeignClient(CommandFeignApi api) {
        this.api = api;
    }

    @Override
    public PlayerQuery findById(Long id) {
        return api.findPlayer(id).data().toQuery();
    }
}
