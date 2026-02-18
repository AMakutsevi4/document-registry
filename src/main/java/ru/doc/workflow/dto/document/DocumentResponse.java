package ru.doc.workflow.dto.document;

import ru.doc.workflow.enums.DocumentStatus;

import java.time.LocalDateTime;

public record DocumentResponse(
        Long id,
        String number,
        String author,
        String title,
        DocumentStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
