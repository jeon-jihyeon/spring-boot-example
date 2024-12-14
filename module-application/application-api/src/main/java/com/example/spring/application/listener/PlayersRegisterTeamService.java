package com.example.spring.application.listener;

import com.example.spring.domain.player.command.PlayersRegisterTeamCommand;
import com.example.spring.domain.player.repository.PlayerBulkCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayersRegisterTeamService {
    private final PlayerBulkCommandRepository bulkCommandRepository;

    public PlayersRegisterTeamService(PlayerBulkCommandRepository bulkCommandRepository) {
        this.bulkCommandRepository = bulkCommandRepository;
    }

    @Transactional
    public void invoke(PlayersRegisterTeamCommand command) {
        bulkCommandRepository.updateAll(command.teamId(), command.playerIds());
    }
}
