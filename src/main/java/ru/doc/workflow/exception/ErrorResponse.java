package ru.doc.workflow.exception;

public record ErrorResponse(
        String code,
        String message
) {}
