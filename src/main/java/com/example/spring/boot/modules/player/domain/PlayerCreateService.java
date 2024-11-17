package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.player.domain.model.Player;
import com.example.spring.boot.modules.player.domain.model.PlayerId;
import com.example.spring.boot.modules.player.domain.repository.PlayerCommandRepository;
import com.example.spring.boot.modules.player.domain.repository.PlayerQueryRepository;
import com.example.spring.boot.modules.player.domain.repository.command.PlayerCreateCommand;
import com.example.spring.boot.modules.player.domain.repository.query.PlayerQuery;
import org.springframework.stereotype.Service;

@Service
public class PlayerCreateService {
    private final PlayerCommandRepository commandRepository;
    private final PlayerQueryRepository queryRepository;

    public PlayerCreateService(PlayerCommandRepository commandRepository, PlayerQueryRepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    public PlayerQuery invoke(PlayerCreateCommand command) {
        final PlayerId id = commandRepository.save(Player.create(command));
        return queryRepository.findPlayer(id);
    }
}
