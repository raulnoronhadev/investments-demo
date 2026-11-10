CREATE TABLE user (
    id         BIGSERIAL PRIMARY KEY,
    nome       VARCHAR(120) NOT NULL,
    email      VARCHAR(180) NOT NULL UNIQUE,
    data_created  TIMESTAMP    NOT NULL DEFAULT NOW()
);