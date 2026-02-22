package ru.doc.workflow.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.entity.History;
import ru.doc.workflow.enums.ActionType;
import ru.doc.workflow.reposiory.DocumentHistoryRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditServiceImpl {

    private final DocumentHistoryRepository historyRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public void logAction(Document document, String initiator, ActionType action, String comment) {
        log.debug("Logging history: documentId={}, action={}, initiator={}",
                document.getId(), action, initiator);

        History history = History.builder()
                .document(document)
                .initiator(initiator)
                .actionType(action)
                .comment(comment)
                .build();
        historyRepository.save(history);
    }
}
