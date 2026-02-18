package ru.doc.workflow.dto.history;

import ru.doc.workflow.enums.ActionType;

import java.time.LocalDateTime;

public record HistoryResponse(
        String initiator,
        ActionType actionType,
        String comment,
        LocalDateTime createdAt
) {}