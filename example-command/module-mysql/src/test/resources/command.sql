CREATE TABLE players
(
    id         BIGINT       NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    grade      VARCHAR(1)   NOT NULL,
    point      INT          NOT NULL,
    first_name VARCHAR(30)  NOT NULL,
    last_name  VARCHAR(30)  NOT NULL,
    team_id    BIGINT       NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE outbox_events
(
    id           BIGINT       NOT NULL,
    completed    BIT          NOT NULL,
    type         VARCHAR(10)  NOT NULL,
    entity_id    BIGINT       NOT NULL,
    completed_at TIMESTAMP(6),
    created_at   TIMESTAMP(6) NOT NULL,
    updated_at   TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);