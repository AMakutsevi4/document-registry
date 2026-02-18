package ru.doc.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.entity.DocumentHistory;
import ru.doc.workflow.enums.ActionType;
import ru.doc.workflow.reposiory.DocumentHistoryRepository;
import ru.doc.workflow.service.AuditService;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final DocumentHistoryRepository historyRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    @Override
    public void logAction(Document document, String initiator, ActionType action, String comment) {
        log.debug("Logging history: documentId={}, action={}, initiator={}",
                document.getId(), action, initiator);

        DocumentHistory history = DocumentHistory.builder()
                .document(document)
                .initiator(initiator)
                .actionType(action)
                .comment(comment)
                .build();
        historyRepository.save(history);
    }
}
