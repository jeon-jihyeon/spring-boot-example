package com.example.spring.infrastructure.db.command.player;

import com.example.spring.domain.player.PlayerCommandRepository;
import com.example.spring.domain.player.dto.PlayerData;
import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.spring.infrastructure.db.command.player.QPlayerEntity.playerEntity;


@Repository
public class PlayerCommandAdapter implements PlayerCommandRepository {
    private final PlayerJpaRepository repository;
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerCommandAdapter(PlayerJpaRepository repository, JPAQueryFactory commandQueryFactory) {
        this.repository = repository;
        this.jpaQueryFactory = commandQueryFactory;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public PlayerData save(PlayerData player) {
        return repository.save(PlayerEntity.from(player)).toData();
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void updateAll(TeamId teamId, List<Long> playerIds) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.id.in(playerIds))
                .set(playerEntity.teamId, teamId.value())
                .execute();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void updateAll(TeamId teamId) {
        jpaQueryFactory.update(playerEntity)
                .where(playerEntity.teamId.eq(teamId.value()))
                .set(playerEntity.teamId, TeamId.NoTeam.value())
                .execute();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }
}
