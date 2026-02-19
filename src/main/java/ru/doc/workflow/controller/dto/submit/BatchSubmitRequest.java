package ru.doc.workflow.controller.dto.submit;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BatchSubmitRequest(

        @NotEmpty(message = "Ids must not be empty")
        @Size(max = 1000, message = "Maximum 1000 ids allowed")
        List<Long> ids,

        @NotBlank(message = "Initiator is required")
        String initiator,

        String comment
) {}
