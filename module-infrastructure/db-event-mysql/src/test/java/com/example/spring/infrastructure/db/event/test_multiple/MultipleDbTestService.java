package com.example.spring.infrastructure.db.event.test_multiple;

import com.example.spring.domain.event.DomainEvent;
import com.example.spring.infrastructure.db.event.BaseEmbeddedDbTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MultipleDbTestService extends BaseEmbeddedDbTest {
    private final TxInboxService txInboxService;
    private final TxOutboxService txOutboxService;

    public MultipleDbTestService(TxInboxService txInboxService, TxOutboxService txOutboxService) {
        this.txInboxService = txInboxService;
        this.txOutboxService = txOutboxService;
    }

    void saveWithExceptionInNoTx(DomainEvent event) {
        txInboxService.save(event);
        txOutboxService.save(event);
        throw new MultipleDbException();
    }

    void saveWithGlobalExceptionAndEachTx(DomainEvent event) {
        txInboxService.saveInTx(event);
        txOutboxService.saveInTx(event);
        throw new MultipleDbException();
    }

    @Transactional
    void saveWithGlobalTxAndExceptionAndNewTx(DomainEvent event) {
        txInboxService.saveInTx(event);
        txOutboxService.saveInNewTx(event);
        throw new MultipleDbException();
    }

    void saveWithInboxExceptionAtFirst(DomainEvent event) {
        txInboxService.saveWithException(event);
        txOutboxService.saveInTx(event);
    }

    void saveWithInboxExceptionAtLast(DomainEvent event) {
        txOutboxService.saveInTx(event);
        txInboxService.saveWithException(event);
    }

    @Transactional
    void saveWithInboxExceptionAtLastAndGlobalTx(DomainEvent event) {
        txOutboxService.saveInTx(event);
        txInboxService.saveWithException(event);
    }
}
