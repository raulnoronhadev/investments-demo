CREATE TABLE selic_rate (
    id              BIGSERIAL PRIMARY KEY,
    annual_rate     NUMERIC(9, 4) NOT NULL,
    reference_date  DATE          NOT NULL UNIQUE,
    fetched_at      TIMESTAMP     NOT NULL
);