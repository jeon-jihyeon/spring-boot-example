package com.example.spring.infrastructure.db.event.test_multiple;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.domain.event.Layer;
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
        return new DomainEvent(id, Layer.DOMAIN, DomainEvent.Type.CREATE, "model", 2L, false, null, null);
    }

    @Test
    void test() {
        assertThrows(MultipleDbException.class, () -> service.saveWithExceptionInNoTx(getModel(1L)));
        assertThat(inbox.existsById(1L)).isTrue();
        assertThat(outbox.existsById(1L)).isTrue();

        assertThrows(MultipleDbException.class, () -> service.saveWithGlobalExceptionAndEachTx(getModel(10L)));
        assertThat(inbox.existsById(10L)).isTrue();
        assertThat(outbox.existsById(10L)).isTrue();

        assertThrows(NoUniqueBeanDefinitionException.class, () -> service.saveWithGlobalTxAndException(getModel(20L)));
        assertThat(inbox.existsById(20L)).isFalse();
        assertThat(outbox.existsById(20L)).isFalse();

        assertThrows(MultipleDbException.class, () -> service.saveWithInboxExceptionAtFirst(getModel(30L)));
        assertThat(inbox.existsById(30L)).isFalse();
        assertThat(outbox.existsById(30L)).isFalse();

        assertThrows(MultipleDbException.class, () -> service.saveWithInboxExceptionAtLast(getModel(30L)));
        assertThat(inbox.existsById(30L)).isFalse();
        assertThat(outbox.existsById(30L)).isTrue();

        assertThrows(MultipleDbException.class, () -> service.saveWithInboxExceptionAtFistAndNewTx(getModel(50L)));
        assertThat(inbox.existsById(50L)).isFalse();
        assertThat(outbox.existsById(50L)).isFalse();
    }
}
