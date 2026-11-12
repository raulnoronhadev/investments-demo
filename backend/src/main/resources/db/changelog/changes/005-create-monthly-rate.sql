CREATE TABLE monthly_rate (
    id              BIGSERIAL PRIMARY KEY,
    series          VARCHAR(16)   NOT NULL,
    reference_month DATE          NOT NULL,
    value           NUMERIC(9, 4) NOT NULL,
    fetched_at      TIMESTAMP     NOT NULL,
    CONSTRAINT uq_monthly_rate_series_month UNIQUE (series, reference_month)
);
