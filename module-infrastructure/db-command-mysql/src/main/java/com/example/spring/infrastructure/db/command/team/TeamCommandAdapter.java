package com.example.spring.infrastructure.db.command.team;

import com.example.spring.domain.player.model.PlayerId;
import com.example.spring.domain.team.TeamCommandRepository;
import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.domain.team.model.TeamId;
import com.example.spring.infrastructure.db.EntityNotFoundException;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static com.example.spring.infrastructure.db.command.team.QTeamEntity.teamEntity;

@Repository
public class TeamCommandAdapter implements TeamCommandRepository {
    public final JPAQueryFactory queryFactory;
    private final TeamJpaRepository repository;

    public TeamCommandAdapter(TeamJpaRepository repository, JPAQueryFactory commandQueryFactory) {
        this.repository = repository;
        this.queryFactory = commandQueryFactory;
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public TeamData save(TeamData team) {
        return repository.save(TeamEntity.from(team)).toData();
    }

    @Override
    public TeamData findById(TeamId id) {
        return repository.findById(id.value()).orElseThrow(EntityNotFoundException::new).toData();
    }

    @Override
    @Transactional(transactionManager = "commandTransactionManager")
    public void deleteById(TeamId id) {
        repository.deleteById(id.value());
    }

    @Override
    public TeamId findTeamId(PlayerId playerId) {
        return queryFactory.select(teamEntity)
                .where(teamEntity.playerIds.contains(playerId.value()))
                .stream().findFirst()
                .map(e -> new TeamId(e.getId()))
                .orElse(TeamId.NoTeam);
    }
}
