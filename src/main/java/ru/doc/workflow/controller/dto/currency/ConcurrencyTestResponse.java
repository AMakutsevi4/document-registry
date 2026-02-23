package ru.doc.workflow.controller.dto.currency;

import lombok.Builder;
import ru.doc.workflow.enums.DocumentStatus;

@Builder
public record ConcurrencyTestResponse(
        Long documentId,
        long successCount,
        long conflictCount,
        long errorCount,
        DocumentStatus finalStatus
) {}
