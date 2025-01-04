package com.example.spring.infrastructure.db.event;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventInbox;
import com.example.spring.domain.event.QueryNewInboxService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventContextTestConfig {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final DomainEventInbox inbox = new DomainEventInbox() {
        @Override
        public void save(DomainEvent event) {
            log.debug("[Inbox] {}", event);
        }

        @Override
        public boolean exists(Long id) {
            log.debug("[Inbox] {}", id);
            return false;
        }
    };

    @Bean
    public QueryNewInboxService inboxService() {
        return new QueryNewInboxService(inbox);
    }
}
