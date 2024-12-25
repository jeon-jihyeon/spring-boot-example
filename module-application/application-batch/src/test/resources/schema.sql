CREATE TABLE IF NOT EXISTS outbox_events
(
    id           BIGINT       NOT NULL,
    layer        VARCHAR(12)  NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    model_name   VARCHAR(64)  NOT NULL,
    model_id     BIGINT       NOT NULL,
    completed    BIT          NOT NULL,
    completed_at TIMESTAMP(6),
    created_at   TIMESTAMP(6) NOT NULL,
    updated_at   TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);