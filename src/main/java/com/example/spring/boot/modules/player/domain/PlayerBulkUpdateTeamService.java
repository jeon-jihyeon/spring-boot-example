package com.example.spring.boot.modules.player.domain;

import com.example.spring.boot.modules.player.domain.repository.PlayerBulkCommandRepository;
import com.example.spring.boot.modules.player.domain.repository.command.PlayerBulkUpdateTeamCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlayerBulkUpdateTeamService {
    private final PlayerBulkCommandRepository bulkCommandRepository;

    public PlayerBulkUpdateTeamService(PlayerBulkCommandRepository bulkCommandRepository) {
        this.bulkCommandRepository = bulkCommandRepository;
    }

    @Transactional
    public void invoke(PlayerBulkUpdateTeamCommand command) {
        bulkCommandRepository.updateAllTeam(command.teamId(), command.newId());
    }
}
