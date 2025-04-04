package com.example.spring.mongodb.inbox;

import com.example.spring.domain.event.InboxEvent;
import com.example.spring.domain.event.InboxEventRepository;
import org.springframework.stereotype.Repository;

@Repository
public class InboxEventAdapter implements InboxEventRepository {
    private final InboxEventMongoRepository repository;

    public InboxEventAdapter(InboxEventMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(InboxEvent event) {
        repository.save(InboxEventDocument.from(event));
    }

    @Override
    public boolean exists(Long id) {
        return repository.existsById(id);
    }
}
