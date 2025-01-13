package com.example.spring.infrastructure.db.command.test;

import com.example.spring.infrastructure.db.command.BaseEmbeddedDbTest;
import com.example.spring.infrastructure.db.command.player.PlayerJpaRepository;
import com.example.spring.infrastructure.db.command.team.TeamJpaRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RepositoryBatchTest extends BaseEmbeddedDbTest {
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final long MILLION = 1_000_000;
    private static final String INSERT = "INSERT INTO teams (id, name, starts_at, created_at, updated_at) VALUES (?, ?, ?, ?, ?)";
    @Autowired
    private TeamJpaRepository teamRepository;
    @Autowired
    private PlayerJpaRepository playerRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        List<Long> list = new ArrayList<>();
        for (long id = 1L; id <= MILLION; id++) list.add(id);
        jdbcTemplate.batchUpdate(INSERT, list, list.size(), (ps, e) -> {
            ps.setLong(1, e);
            ps.setString(2, String.format("name-%d", e));
            ps.setTimestamp(3, Timestamp.valueOf(NOW));
            ps.setTimestamp(4, Timestamp.valueOf(NOW));
            ps.setTimestamp(5, Timestamp.valueOf(NOW));
        });
    }

    @Test
    void test() {
        assertThat(teamRepository.count()).isEqualTo(MILLION);
    }
}
