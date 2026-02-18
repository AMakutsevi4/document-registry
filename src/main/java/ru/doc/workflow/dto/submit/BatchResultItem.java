package ru.doc.workflow.dto.submit;

import ru.doc.workflow.enums.BatchStatus;

public record BatchResultItem(
        Long id,
        BatchStatus status
) {}
