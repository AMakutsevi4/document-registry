package ru.doc.workflow.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.doc.workflow.entity.ApprovalRegistry;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.ActionType;
import ru.doc.workflow.enums.DocumentStatus;
import ru.doc.workflow.exception.RegistryException;
import ru.doc.workflow.reposiory.ApprovalRegistryRepository;
import ru.doc.workflow.reposiory.DocumentRepository;

@Service
@RequiredArgsConstructor
public class DocumentStatusServiceImpl {

    private final DocumentRepository documentRepository;
    private final ApprovalRegistryRepository approvalRegistryRepository;
    private final AuditServiceImpl auditService;


    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void submit(Long id, String initiator, String comment) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Document not found: " + id));

        if (document.getStatus() != DocumentStatus.DRAFT) {
            throw new IllegalStateException("Invalid status transition");
        }
        document.setStatus(DocumentStatus.SUBMITTED);
        documentRepository.save(document);
        auditService.logAction(document, initiator, ActionType.SUBMIT, comment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void approve(Long id, String initiator, String comment) {
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Document not found: " + id));

        if (document.getStatus() != DocumentStatus.SUBMITTED) {
            throw new IllegalStateException("Document is not in SUBMITTED status");
        }
        document.setStatus(DocumentStatus.APPROVED);
        documentRepository.save(document);
        auditService.logAction(document, initiator, ActionType.APPROVE, comment);
        try {
            ApprovalRegistry registry = createNew(document);
            approvalRegistryRepository.save(registry);
        } catch (Exception e) {
            throw new RegistryException("Failed to create registry record", e);
        }
    }

    public static ApprovalRegistry createNew(Document document) {
        ApprovalRegistry registry = new ApprovalRegistry();
        registry.setDocument(document);
        return registry;
    }
}
