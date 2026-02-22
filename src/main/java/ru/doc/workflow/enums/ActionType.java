package ru.doc.workflow.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActionType {
    CREATE("Создан"),
    SUBMIT("Отправлен"),
    APPROVE("Согласован");

    private final String value;
}
