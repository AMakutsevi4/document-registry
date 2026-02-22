--liquibase formatted sql
--changeset Alexandr Makutsevich:2026-02-18--create-documents-table.sql

CREATE TABLE documents (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(255) UNIQUE NOT NULL,
    author VARCHAR(255) NOT NULL,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0
);