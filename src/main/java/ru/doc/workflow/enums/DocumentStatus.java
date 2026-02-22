package ru.doc.workflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocumentStatus {
    DRAFT("Черновик"),
    SUBMITTED("Отправлен"),
    APPROVED("Согласован");

    private final String value;
}
