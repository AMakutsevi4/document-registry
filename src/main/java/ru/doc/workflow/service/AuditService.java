package ru.doc.workflow.service;

import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.ActionType;

public interface AuditService {
    void logAction(Document document, String initiator, ActionType action, String comment);
}
