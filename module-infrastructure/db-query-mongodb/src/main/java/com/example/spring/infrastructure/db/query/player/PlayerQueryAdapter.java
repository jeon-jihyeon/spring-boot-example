package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.command.player.dto.PlayerData;
import com.example.spring.domain.command.player.model.PlayerId;
import com.example.spring.domain.command.team.model.TeamId;
import com.example.spring.domain.query.player.PlayerQueryRepository;
import com.example.spring.infrastructure.db.query.DocumentNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerQueryAdapter implements PlayerQueryRepository {
    private final PlayerMongoRepository repository;

    public PlayerQueryAdapter(PlayerMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public PlayerData create(PlayerData player) {
        return repository.save(PlayerDocument.create(player)).toData();
    }

    @Override
    public PlayerData update(PlayerData player) {
        return repository.save(repository.findById(player.id().value()).orElseThrow(DocumentNotFoundException::new).update(player)).toData();
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return repository.findById(id.value()).orElseThrow(DocumentNotFoundException::new).toData();
    }

    @Override
    public List<PlayerData> findAllByTeamIds(List<TeamId> teamIds) {
        return repository.findAllByTeamIdIn(teamIds.stream().map(TeamId::value).toList()).stream().map(PlayerDocument::toData).toList();
    }

    @Override
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
