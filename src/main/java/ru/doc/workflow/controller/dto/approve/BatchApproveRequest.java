package ru.doc.workflow.controller.dto.approve;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BatchApproveRequest(
        @NotEmpty(message = "Идентификаторы не должны быть пустыми")
        @Size(max = 1000, message = "Максимум 1000 идентификаторов")
        List<Long> ids,
        @NotBlank String initiator,
        String comment
) {}
