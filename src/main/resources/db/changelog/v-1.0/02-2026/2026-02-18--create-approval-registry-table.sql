--liquibase formatted sql
--changeset Alexandr Makutsevich:2026-02-18--create-approval-registry-table.sql

CREATE TABLE approval_registry (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT UNIQUE REFERENCES documents(id),
    approved_at TIMESTAMP NOT NULL
);