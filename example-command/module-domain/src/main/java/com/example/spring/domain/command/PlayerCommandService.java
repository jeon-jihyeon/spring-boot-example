package com.example.spring.domain.command;

import com.example.spring.domain.DistributedLock;
import com.example.spring.domain.command.dto.PlayerAddPointCommand;
import com.example.spring.domain.command.dto.PlayerCreateCommand;
import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.Player;
import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.outbox.OutboxEventSaveService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerCommandService {
    private final PlayerCommandRepository playerCommandRepository;
    private final OutboxEventSaveService outboxEventSaveService;

    public PlayerCommandService(PlayerCommandRepository playerCommandRepository, OutboxEventSaveService outboxEventSaveService) {
        this.playerCommandRepository = playerCommandRepository;
        this.outboxEventSaveService = outboxEventSaveService;
    }

    public PlayerData read(PlayerId id) {
        return playerCommandRepository.findById(id);
    }

    @Transactional
    public PlayerData create(PlayerCreateCommand command) {
        final PlayerData created = playerCommandRepository.save(PlayerData.from(Player.create(command)));
        outboxEventSaveService.savePlayerCreatedEvent(created.id());
        return created;
    }

    @Transactional
    public void delete(PlayerId id) {
        playerCommandRepository.deleteById(id);
        outboxEventSaveService.savePlayerDeletedEvent(id);
    }

    @Transactional
    @DistributedLock(key = "#command.getKey()")
    public void addPoint(PlayerAddPointCommand command) {
        final Player player = playerCommandRepository.findById(command.playerId()).toModel();
        playerCommandRepository.save(PlayerData.from(player.addPoint(command)));
        outboxEventSaveService.savePlayerPointAddedEvent(command.playerId());
    }
}
