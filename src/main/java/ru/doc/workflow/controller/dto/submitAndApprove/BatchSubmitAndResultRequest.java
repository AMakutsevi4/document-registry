package ru.doc.workflow.controller.dto.submitAndApprove;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record BatchSubmitAndResultRequest(

        @NotEmpty(message = "Идентификаторы не должны быть пустыми")
        @Size(max = 1000, message = "Максимум 1000 идентификаторов")
        List<Long> ids,

        @NotBlank(message = "Требуется инициатор")
        String initiator,

        String comment
) {
}
