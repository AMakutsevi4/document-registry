--liquibase formatted sql
--changeset Alexandr Makutsevich:2026-02-18--add-indexes-to-documents.sql
CREATE INDEX idx_document_history_document_id ON document_history (document_id);

CREATE INDEX idx_documents_created_at ON documents (created_at DESC);

CREATE INDEX idx_document_status ON documents(status);

CREATE INDEX idx_document_author ON documents(author);

CREATE INDEX idx_document_created_at ON documents(created_at);
