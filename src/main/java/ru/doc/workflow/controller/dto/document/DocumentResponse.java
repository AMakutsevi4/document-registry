package ru.doc.workflow.controller.dto.document;

import ru.doc.workflow.enums.DocumentStatus;

import java.time.LocalDateTime;

public record DocumentResponse(
        Long id,
        String number,
        String author,
        DocumentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
