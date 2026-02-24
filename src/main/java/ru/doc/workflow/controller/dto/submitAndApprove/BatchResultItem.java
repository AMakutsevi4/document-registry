package ru.doc.workflow.controller.dto.submitAndApprove;

import ru.doc.workflow.enums.BatchStatus;

public record BatchResultItem(
        Long id,
        BatchStatus status
) {}
