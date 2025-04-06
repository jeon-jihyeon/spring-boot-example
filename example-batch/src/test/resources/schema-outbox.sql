CREATE TABLE IF NOT EXISTS outbox_events
(
    id           BIGINT       NOT NULL,
    completed    BIT          NOT NULL,
    type         VARCHAR(16)  NOT NULL,
    entity_id    BIGINT       NOT NULL,
    completed_at TIMESTAMP(6),
    created_at   TIMESTAMP(6) NOT NULL,
    updated_at   TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);