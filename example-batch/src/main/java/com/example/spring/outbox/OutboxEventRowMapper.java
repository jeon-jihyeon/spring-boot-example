package com.example.spring.outbox;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class OutboxEventRowMapper implements RowMapper<OutboxEvent> {
    @Override
    public OutboxEvent mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new OutboxEvent(
                rs.getLong("id"),
                rs.getBoolean("completed"),
                OutboxEventType.valueOf(rs.getString("type")),
                rs.getLong("entity_id"),
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("completed_at", LocalDateTime.class)
        );
    }
}
