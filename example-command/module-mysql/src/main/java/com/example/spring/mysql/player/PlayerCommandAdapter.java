package com.example.spring.mysql.player;

import com.example.spring.domain.command.PlayerCommandRepository;
import com.example.spring.domain.command.dto.PlayerData;
import com.example.spring.domain.command.model.PlayerId;
import com.example.spring.domain.command.model.TeamId;
import com.example.spring.mysql.EntityNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.spring.mysql.player.QPlayerEntity.playerEntity;


@Repository
public class PlayerCommandAdapter implements PlayerCommandRepository {
    public final JPAQueryFactory queryFactory;
    private final PlayerJpaRepository repository;

    public PlayerCommandAdapter(JPAQueryFactory commandQueryFactory, PlayerJpaRepository repository) {
        this.queryFactory = commandQueryFactory;
        this.repository = repository;
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
    public void deleteById(PlayerId id) {
        repository.deleteById(id.value());
    }

    @Override
    public List<PlayerId> findIdsByTeamId(TeamId teamId) {
        return repository.findAllByTeamId(teamId.value()).stream().map(PlayerEntity::getId).map(PlayerId::new).toList();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void leaveAll(TeamId teamId) {
        queryFactory.update(playerEntity)
                .set(playerEntity.teamId, TeamId.NoTeam.value())
                .where(playerEntity.teamId.eq(teamId.value()))
                .execute();
    }
}
