ALTER TABLE investment ALTER COLUMN account_id DROP DEFAULT;
DROP SEQUENCE IF EXISTS investment_account_id_seq;
ALTER TABLE investment ALTER COLUMN account_id TYPE BIGINT;

CREATE INDEX idx_investment_account ON investment (account_id);