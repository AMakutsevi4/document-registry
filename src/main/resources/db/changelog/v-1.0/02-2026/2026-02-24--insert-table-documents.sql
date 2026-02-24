--liquibase formatted sql
--changeset Alexandr Makutsevich:2026-02-24--insert-table-documents.sql

INSERT INTO documents (number, author, title, status, created_at, updated_at, version)
SELECT
    'DOC-SEED-' || i,
    'System Generator',
    'Test Document #' || i,
    'DRAFT',
    now(),
    now(),
    0
FROM generate_series(1, 20) AS i;

INSERT INTO document_history (document_id, initiator, action_type, comment, created_at)
SELECT
    id,
    'System',
    'CREATE',
    'Initial demo',
    now()
FROM documents
WHERE author = 'System Generator';