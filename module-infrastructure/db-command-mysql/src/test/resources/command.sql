CREATE TABLE players
(
    id         BIGINT       NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    first_name VARCHAR(30)  NOT NULL,
    grade      VARCHAR(1)   NOT NULL,
    last_name  VARCHAR(30)  NOT NULL,
    team_id    BIGINT       NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE teams
(
    id         BIGINT       NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    name       VARCHAR(30)  NOT NULL,
    starts_at  TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);