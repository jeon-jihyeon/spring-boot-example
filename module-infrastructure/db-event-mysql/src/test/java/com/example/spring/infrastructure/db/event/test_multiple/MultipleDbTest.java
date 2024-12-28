package com.example.spring.infrastructure.db.event.test_multiple;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.DomainEventLayer;
import com.example.spring.domain.event.DomainEventType;
import com.example.spring.infrastructure.db.event.BaseEmbeddedDbTest;
import com.example.spring.infrastructure.db.event.inbox.InboxEventJpaRepository;
import com.example.spring.infrastructure.db.event.outbox.OutboxEventJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MultipleDbTest extends BaseEmbeddedDbTest {
    private final InboxEventJpaRepository inbox;
    private final OutboxEventJpaRepository outbox;
    private final MultipleDbTestService service;

    public MultipleDbTest(InboxEventJpaRepository inbox, OutboxEventJpaRepository outbox, MultipleDbTestService service) {
        this.inbox = inbox;
        this.outbox = outbox;
        this.service = service;
    }

    DomainEvent getModel(Long id) {
        return new DomainEvent(id, DomainEventLayer.DOMAIN, DomainEventType.CREATE, "model", 2L, false, null, null);
    }

    @Test
    void test() {
        assertThrows(MultipleDbException.class, () -> service.saveWithExceptionInNoTx(getModel(1L)));
        assertThat(inbox.existsById(1L)).isTrue();
        assertThat(outbox.existsById(1L)).isTrue();

        assertThrows(MultipleDbException.class, () -> service.saveWithGlobalExceptionAndEachTx(getModel(10L)));
        assertThat(inbox.existsById(10L)).isTrue();
        assertThat(outbox.existsById(10L)).isTrue();

        assertThrows(NoUniqueBeanDefinitionException.class, () -> service.saveWithGlobalTxAndExceptionAndNewTx(getModel(20L)));

        assertThrows(MultipleDbException.class, () -> service.saveWithInboxExceptionAtFirst(getModel(30L)));
        assertThat(inbox.existsById(30L)).isFalse();
        assertThat(outbox.existsById(30L)).isFalse();

        assertThrows(MultipleDbException.class, () -> service.saveWithInboxExceptionAtLast(getModel(40L)));
        assertThat(inbox.existsById(40L)).isFalse();
        assertThat(outbox.existsById(40L)).isTrue();

        assertThrows(NoUniqueBeanDefinitionException.class, () -> service.saveWithInboxExceptionAtLastAndGlobalTx(getModel(50L)));
    }
}
