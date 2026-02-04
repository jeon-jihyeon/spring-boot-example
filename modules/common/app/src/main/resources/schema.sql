CREATE TABLE IF NOT EXISTS candle_entity (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    symbol     VARCHAR(16)     NOT NULL,
    currency   VARCHAR(3)      NOT NULL,
    start_time TIMESTAMP       NOT NULL,
    open       DECIMAL(30, 8)  NOT NULL,
    high       DECIMAL(30, 8)  NOT NULL,
    low        DECIMAL(30, 8)  NOT NULL,
    close      DECIMAL(30, 8)  NOT NULL,
    volume     DECIMAL(20, 8)  NOT NULL,
    turnover   DECIMAL(40, 8)  NOT NULL,
    created_at TIMESTAMP       NOT NULL,
    updated_at TIMESTAMP       NOT NULL
);
