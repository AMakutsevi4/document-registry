package ru.doc.workflow.controller.dto.document;

import jakarta.validation.constraints.NotBlank;

public record DocumentRequest(
        @NotBlank(message = "Требуется автор")
        String author,

        @NotBlank(message = "Требуется название")
        String title,

        @NotBlank(message = "Требуется инициатор")
        String initiator
){}