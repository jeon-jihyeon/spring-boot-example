package com.example.spring.application.listener;

import com.example.spring.domain.player.repository.PlayerBulkCommandRepository;
import com.example.spring.domain.player.repository.command.PlayersUpdateTeamCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayersUpdateTeamService {
    private final PlayerBulkCommandRepository bulkCommandRepository;

    public PlayersUpdateTeamService(PlayerBulkCommandRepository bulkCommandRepository) {
        this.bulkCommandRepository = bulkCommandRepository;
    }

    @Transactional
    public void invoke(PlayersUpdateTeamCommand command) {
        bulkCommandRepository.updateAll(command.teamId(), command.newId());
    }
}
