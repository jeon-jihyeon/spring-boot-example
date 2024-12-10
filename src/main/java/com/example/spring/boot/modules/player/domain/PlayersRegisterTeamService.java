package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.player.domain.repository.PlayerBulkCommandRepository;
import com.example.spring.boot.modules.player.domain.repository.command.PlayersRegisterTeamCommand;
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
