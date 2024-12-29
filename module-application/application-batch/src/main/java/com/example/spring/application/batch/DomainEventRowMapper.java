package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventType;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DomainEventRowMapper implements RowMapper<DomainEvent> {
    @Override
    public DomainEvent mapRow(@NonNull ResultSet rs, int rowNum) throws SQLException {
        return new DomainEvent(
                rs.getLong("id"),
                rs.getBoolean("completed"),
                DomainEventType.valueOf(rs.getString("type")),
                rs.getString("model_name"),
                rs.getLong("model_id"),
                rs.getObject("created_at", LocalDateTime.class),
                rs.getObject("completed_at", LocalDateTime.class)
        );
    }
}
