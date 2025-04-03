package com.example.spring.mongodb.player;

import com.example.spring.domain.query.PlayerQuery;
import com.example.spring.domain.query.PlayerQueryRepository;
import com.example.spring.mongodb.DocumentNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PlayerQueryAdapter implements PlayerQueryRepository {
    private final PlayerMongoRepository repository;

    public PlayerQueryAdapter(PlayerMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public PlayerQuery create(PlayerQuery player) {
        return repository.save(PlayerDocument.create(player)).toQuery();
    }

    @Override
    @Transactional
    public PlayerQuery update(PlayerQuery player) {
        return repository.save(repository.findById(player.id()).orElseThrow(DocumentNotFoundException::new).update(player)).toQuery();
    }

    @Override
    public PlayerQuery findById(Long id) {
        return repository.findById(id).orElseThrow(DocumentNotFoundException::new).toQuery();
    }

    @Override
    public List<PlayerQuery> findPlayersByTeamId(Long teamId) {
        return repository.findAllByTeamId(teamId).stream().map(PlayerDocument::toQuery).toList();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
