package ru.doc.workflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.ActionType;
import ru.doc.workflow.enums.DocumentStatus;
import ru.doc.workflow.reposiory.DocumentRepository;
import ru.doc.workflow.service.AuditService;
import ru.doc.workflow.service.DocumentStatusService;

@Service
@RequiredArgsConstructor
public class DocumentStatusServiceImpl implements DocumentStatusService {

    private final DocumentRepository documentRepository;
    private final AuditService auditService;


    @Override
    @Transactional
    public void submit(Long id, String initiator, String comment) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Document not found: " + id));


        if (document.getStatus() != DocumentStatus.DRAFT) {
            throw new IllegalStateException("Invalid status transition");
        }
        document.setStatus(DocumentStatus.SUBMITTED);

        auditService.logAction(
                document,
                initiator,
                ActionType.SUBMIT,
                comment
        );
    }
}
