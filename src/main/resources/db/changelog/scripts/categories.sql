-- liquibase formatted sql

-- changeset ezuykow:1
CREATE TABLE categories
(
    category_id   SERIAL PRIMARY KEY,
    category_name VARCHAR(50) UNIQUE NOT NULL,
    parent        INT                NULL REFERENCES categories (category_id) ON DELETE CASCADE,
    owner_id      BIGINT             NOT NULL
);

