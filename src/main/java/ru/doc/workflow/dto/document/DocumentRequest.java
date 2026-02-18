package ru.doc.workflow.dto.document;

import jakarta.validation.constraints.NotBlank;

public record DocumentRequest(
        @NotBlank(message = "Author is required")
        String author,

        @NotBlank(message = "Title is required")
        String title,

        @NotBlank(message = "Initiator is required")
        String initiator
){}