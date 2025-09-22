DROP TYPE IF EXISTS transaction_status CASCADE;
DROP TYPE IF EXISTS transaction_type CASCADE;
DROP TYPE IF EXISTS account_status CASCADE;
DROP TYPE IF EXISTS user_role CASCADE;

DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS customer_accounts CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS users CASCADE;

CREATE TYPE transaction_status AS ENUM ('COMPLETED', 'FAILED');
CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'DEPOSIT', 'WITHDRAW');
CREATE TYPE account_status AS ENUM ('ACTIVE', 'INACTIVE');
CREATE TYPE user_role AS ENUM ('CUSTOMER', 'ADMIN');

CREATE CAST (varchar AS transaction_status) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS transaction_type) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS account_status) WITH INOUT AS IMPLICIT;
CREATE CAST (varchar AS user_role) WITH INOUT AS IMPLICIT;

CREATE TABLE addresses
(
    id            UUID PRIMARY KEY,
    street_name   TEXT      NOT NULL,
    street_number TEXT      NOT NULL,
    floor         TEXT,
    door          TEXT,
    postal_code   TEXT      NOT NULL,
    city          TEXT      NOT NULL,
    province      TEXT      NOT NULL,
    deleted       BOOLEAN   NOT NULL,
    created_at    TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP,
    deleted_at    TIMESTAMP

);

CREATE TABLE users
(
    id           UUID PRIMARY KEY,
    display_name TEXT      NOT NULL,
    email        TEXT      NOT NULL UNIQUE,
    password     TEXT      NOT NULL,
    role         user_role NOT NULL,
    created_at   TIMESTAMP NOT NULL,
    updated_at   TIMESTAMP,
    deleted_at   TIMESTAMP
);

CREATE TABLE customers
(
    id          UUID PRIMARY KEY,
    name        TEXT      NOT NULL,
    last_name   TEXT      NOT NULL,
    document_id TEXT      NOT NULL UNIQUE,
    age         INTEGER   NOT NULL,
    address     UUID      NOT NULL,
    phone       TEXT      NOT NULL UNIQUE,
    user_id     UUID      NOT NULL UNIQUE,
    deleted     BOOLEAN   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP,
    FOREIGN KEY (address) REFERENCES addresses (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE accounts
(
    id         UUID PRIMARY KEY,
    balance    NUMERIC(19, 2) NOT NULL,
    iban       TEXT           NOT NULL UNIQUE,
    status     account_status NOT NULL,
    created_at TIMESTAMP      NOT NULL,
    updated_at TIMESTAMP,
    deleted_at TIMESTAMP
);

CREATE TABLE transactions
(
    id               UUID PRIMARY KEY,
    origin_account   UUID               NOT NULL,
    amount           NUMERIC(19, 2)     NOT NULL,
    beneficiary_name TEXT,
    concept          TEXT,
    beneficiary_iban TEXT,
    status           transaction_status NOT NULL,
    type             transaction_type   NOT NULL,
    created_at       TIMESTAMP          NOT NULL,
    updated_at       TIMESTAMP,
    FOREIGN KEY (origin_account) REFERENCES accounts (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE customer_accounts
(
    id          UUID PRIMARY KEY,
    customer_id UUID      NOT NULL,
    account_id  UUID      NOT NULL,
    is_owner    BOOLEAN   NOT NULL,
    deleted     BOOLEAN   NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP,
    deleted_at  TIMESTAMP,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE INDEX idx_transactions_origin_account ON transactions(origin_account);
CREATE INDEX idx_customer_accounts_customer_id ON customer_accounts(customer_id);
CREATE INDEX idx_customer_accounts_account_id ON customer_accounts(account_id);