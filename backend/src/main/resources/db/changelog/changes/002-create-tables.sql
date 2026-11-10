CREATE TABLE account (
    id              BIGSERIAL PRIMARY KEY,
    name            VARCHAR(100) NOT NULL,
    country         VARCHAR(50)  NOT NULL
);

CREATE TABLE investment (
    id BIGSERIAL    PRIMARY KEY,
    account_id      BIGSERIAL NOT NULL,
    amount          NUMERIC(12,2) NOT NULL,
    type            VARCHAR(30) NOT NULL,
    maturity_date   DATE,
    rate            NUMERIC(9, 4),

    CONSTRAINT fk_investment_account FOREIGN KEY (account_id) REFERENCES account(id),
    CONSTRAINT check_investment_type CHECK (type IN ('STOCK', 'FUND', 'TREASURY_BOND')),
    CONSTRAINT ck_investments_treasury_fields CHECK (
        (type = 'TREASURY_BOND' AND maturity_date IS NOT NULL AND rate IS NOT NULL) OR
        (type <> 'TREASURY_BOND' AND maturity_date IS NULL AND rate IS NULL)
    )
);