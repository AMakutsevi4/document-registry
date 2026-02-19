package ru.doc.workflow.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.doc.workflow.controller.dto.document.DocumentRequest;
import ru.doc.workflow.controller.dto.document.DocumentResponse;
import ru.doc.workflow.controller.dto.history.DocumentWithHistoryResponse;
import ru.doc.workflow.controller.dto.submit.BatchResultItem;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.ActionType;
import ru.doc.workflow.enums.BatchStatus;
import ru.doc.workflow.enums.DocumentStatus;
import ru.doc.workflow.mapper.DocumentMapper;
import ru.doc.workflow.reposiory.DocumentRepository;


import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl {

    private final DocumentRepository documentRepository;
    private final DocumentStatusServiceImpl statusService;
    private final AuditServiceImpl auditService;
    private final DocumentMapper mapper;

    @Transactional
    public DocumentResponse create(DocumentRequest request) {
        log.info("Creating new document: author={}, title={}", request.author(), request.title());

        Document document = Document.builder()
                .number(generateUniqueNumber())
                .author(request.author())
                .title(request.title())
                .status(DocumentStatus.DRAFT)
                .build();

        Document savedDoc = documentRepository.save(document);

        auditService.logAction(savedDoc,
                request.initiator(),
                ActionType.CREATE,
                "Initial creation");

        return mapper.toResponse(savedDoc);
    }

    @Transactional(readOnly = true)
    public DocumentWithHistoryResponse getById(Long id) {
        return documentRepository.findWithHistoryById(id)
                .map(mapper::toResponseWithHistory)
                .orElseThrow(() -> new EntityNotFoundException("Document not found with id: " + id));
    }

      @Transactional(readOnly = true)
    public Page<DocumentResponse> getByIds(List<Long> ids, Pageable pageable) {
        log.info("Fetching documents batch: count={}", ids.size());
        return documentRepository.findAllByIdIn(ids, pageable)
                .map(mapper::toResponse);
    }

    public List<BatchResultItem> submitBatch(List<Long> ids, String initiator, String comment) {
        log.info("Submitting documents: count={}", ids.size());
        List<BatchResultItem> results = new ArrayList<>();
        for (Long id : ids) {
            try {
                statusService.submit(id, initiator, comment);
                results.add(new BatchResultItem(id, BatchStatus.SUCCESS));
            } catch (EntityNotFoundException e) {
                results.add(new BatchResultItem(id, BatchStatus.NOT_FOUND));
            } catch (IllegalStateException e) {
                results.add(new BatchResultItem(id, BatchStatus.CONFLICT));
            } catch (Exception e) {
                log.error("Unexpected error during submit for id={}", id, e);
                results.add(new BatchResultItem(id, BatchStatus.CONFLICT));
            }
        }

        return results;
    }

    private String generateUniqueNumber() {
        return "DOC-"
                + System.currentTimeMillis()
                + "-"
                + UUID.randomUUID().toString().substring(0, 4)
                .toUpperCase();
    }
}