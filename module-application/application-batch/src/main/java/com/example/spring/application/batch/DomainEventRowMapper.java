package com.example.spring.application.batch;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
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
                Layer.valueOf(rs.getString("layer")),
                DomainEvent.Type.valueOf(rs.getString("type")),
                rs.getString("model_name"),
                rs.getLong("model_id"),
                rs.getBoolean("completed"),
                rs.getObject("completed_at", LocalDateTime.class),
                rs.getObject("created_at", LocalDateTime.class)
        );
    }
}
