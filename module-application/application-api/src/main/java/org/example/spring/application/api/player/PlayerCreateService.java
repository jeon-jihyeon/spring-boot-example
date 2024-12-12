package org.example.spring.application.api.player;

import com.example.spring.domain.player.Player;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.repository.PlayerCommandRepository;
import com.example.spring.domain.player.repository.PlayerQueryRepository;
import com.example.spring.domain.player.repository.command.PlayerCreateCommand;
import com.example.spring.domain.player.repository.query.PlayerQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCreateService {
    private final PlayerCommandRepository commandRepository;
    private final PlayerQueryRepository queryRepository;

    public PlayerCreateService(PlayerCommandRepository commandRepository, PlayerQueryRepository queryRepository) {
        this.commandRepository = commandRepository;
        this.queryRepository = queryRepository;
    }

    @Transactional
    public PlayerQuery invoke(PlayerCreateCommand command) {
        final PlayerId id = commandRepository.save(Player.create(command));
        return queryRepository.findPlayer(id);
    }
}
