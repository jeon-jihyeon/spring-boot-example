package com.example.spring.domain.event;

public interface InboxEventRepository {
    void save(InboxEvent event);

    boolean exists(Long id);
}
