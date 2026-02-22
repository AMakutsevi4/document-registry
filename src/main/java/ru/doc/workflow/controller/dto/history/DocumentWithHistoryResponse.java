package ru.doc.workflow.controller.dto.history;

import ru.doc.workflow.enums.DocumentStatus;

import java.time.LocalDateTime;
import java.util.List;

public record DocumentWithHistoryResponse(
        Long id,
        String number,
        String author,
        String title,
        DocumentStatus status,
        LocalDateTime createdAt,
        List<HistoryResponse> history
) {}
