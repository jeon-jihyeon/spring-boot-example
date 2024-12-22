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
    private final PlayerCommandMapper mapper;
    private final JPAQueryFactory jpaQueryFactory;

    public PlayerCommandAdapter(
            PlayerJpaRepository repository,
            PlayerCommandMapper mapper,
            @Qualifier("commandQueryFactory") JPAQueryFactory commandQueryFactory
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.jpaQueryFactory = commandQueryFactory;
    }

    @Override
    public PlayerData save(PlayerData player) {
        return mapper.toDomain(repository.save(mapper.toEntity(player)));
    }

    @Override
    public PlayerData findById(PlayerId id) {
        return mapper.toDomain(repository.findById(id.value()).orElseThrow(EntityNotFoundException::new));
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
