CREATE TABLE IF NOT EXISTS outbox_events
(
    id           BIGINT       NOT NULL,
    completed    BIT          NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    queue_name   VARCHAR(64)  NOT NULL,
    model_id     BIGINT       NOT NULL,
    completed_at TIMESTAMP(6),
    created_at   TIMESTAMP(6) NOT NULL,
    updated_at   TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);