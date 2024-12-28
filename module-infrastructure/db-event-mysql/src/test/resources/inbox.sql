CREATE TABLE IF NOT EXISTS inbox_events
(
    id           BIGINT       NOT NULL,
    state        VARCHAR(10)  NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    model_name   VARCHAR(64)  NOT NULL,
    model_id     BIGINT       NOT NULL,
    completed_at TIMESTAMP(6),
    processed_at TIMESTAMP(6),
    created_at   TIMESTAMP(6) NOT NULL,
    updated_at   TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);