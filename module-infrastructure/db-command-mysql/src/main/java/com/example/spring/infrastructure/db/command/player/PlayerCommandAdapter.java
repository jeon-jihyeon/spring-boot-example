package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.PlayerCommandRepository;
import com.example.spring.domain.player.PlayerId;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.team.TeamId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.spring.infrastructure.db.command.player.QPlayerEntity.playerEntity;


@Repository
public class PlayerCommandAdapter implements PlayerCommandRepository {
    private final PlayerJpaRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerCommandAdapter(
            PlayerJpaRepository repository,
            @Qualifier("commandQueryFactory") JPAQueryFactory commandQueryFactory
    ) {
        this.repository = repository;
        this.jpaQueryFactory = commandQueryFactory;
    }

    @Override
    public PlayerData save(PlayerData player) {
        return repository.save(PlayerEntity.from(player)).toData();
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    public void updateAll(TeamId teamId, List<Long> playerIds) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.id.in(playerIds))
                .set(playerEntity.teamId, teamId.value())
                .execute();
    }

    @Override
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
