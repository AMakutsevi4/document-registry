--liquibase formatted sql
--changeset Alexandr Makutsevich:2026-02-18--create-document-history-table.sql

CREATE TABLE document_history (
    id BIGSERIAL PRIMARY KEY,
    document_id BIGINT NOT NULL REFERENCES documents(id),
    initiator VARCHAR(255) NOT NULL,
    action_type VARCHAR(50) NOT NULL,
    comment TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT now()
);
