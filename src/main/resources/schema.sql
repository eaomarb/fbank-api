DROP TYPE IF EXISTS transaction_status CASCADE;
DROP TYPE IF EXISTS transaction_type CASCADE;
DROP TYPE IF EXISTS account_status CASCADE;

DROP TABLE IF EXISTS transactions CASCADE;
DROP TABLE IF EXISTS customer_accounts CASCADE;
DROP TABLE IF EXISTS customers CASCADE;
DROP TABLE IF EXISTS accounts CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;

CREATE TYPE transaction_status AS ENUM ('COMPLETED', 'FAILED');
CREATE TYPE transaction_type AS ENUM ('TRANSFER', 'DEPOSIT', 'WITHDRAW');
CREATE TYPE account_status AS ENUM ('ACTIVE', 'INACTIVE');

CREATE TABLE addresses
(
    id            UUID PRIMARY KEY,
    street_name   TEXT NOT NULL,
    street_number TEXT NOT NULL,
    floor         TEXT,
    door          TEXT,
    postal_code   TEXT NOT NULL,
    city          TEXT NOT NULL,
    province      TEXT NOT NULL
);

CREATE TABLE customers
(
    id          UUID PRIMARY KEY,
    name        TEXT    NOT NULL,
    last_name   TEXT    NOT NULL,
    document_id TEXT    NOT NULL UNIQUE,
    age         INTEGER NOT NULL,
    address     UUID    NOT NULL,
    email       TEXT    NOT NULL UNIQUE,
    phone       TEXT    NOT NULL,
    FOREIGN KEY (address) REFERENCES addresses (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE accounts
(
    id      UUID PRIMARY KEY,
    balance NUMERIC(19, 2) NOT NULL,
    iban    TEXT           NOT NULL UNIQUE,
    status  account_status NOT NULL
);


CREATE TABLE transactions
(
    id               UUID PRIMARY KEY,
    transaction_date TIMESTAMP          NOT NULL,
    origin_account   UUID               NOT NULL,
    amount           NUMERIC(19, 2)     NOT NULL,
    beneficiary_name TEXT,
    concept          TEXT,
    beneficiary_iban TEXT,
    status           transaction_status NOT NULL,
    type             transaction_type   NOT NULL,
    FOREIGN KEY (origin_account) REFERENCES accounts (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (beneficiary_iban) REFERENCES accounts (iban) ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE customer_accounts
(
    id          UUID PRIMARY KEY,
    customer_id UUID    NOT NULL,
    account_id  UUID    NOT NULL,
    is_owner    BOOLEAN NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES customers (id) ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (account_id) REFERENCES accounts (id) ON UPDATE NO ACTION ON DELETE NO ACTION
);
