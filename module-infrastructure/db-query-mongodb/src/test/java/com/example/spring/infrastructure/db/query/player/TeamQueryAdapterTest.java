package com.example.spring.infrastructure.db.query.player;

import com.example.spring.domain.team.dto.TeamData;
import com.example.spring.infrastructure.db.query.BaseQueryDbTest;
import com.example.spring.infrastructure.db.query.team.TeamDocument;
import com.example.spring.infrastructure.db.query.team.TeamMongoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class TeamQueryAdapterTest extends BaseQueryDbTest {

    @Autowired
    private TeamMongoRepository repository;

    @Test
    void save() {
        final LocalDateTime now = LocalDateTime.now();
        repository.save(TeamDocument.from(TeamData.of(22L, "name", now, List.of(11L))));
        final TeamData found = repository.findById(22L).orElseThrow().toData();
        assertThat(found.id().value()).isEqualTo(22L);
    }
}