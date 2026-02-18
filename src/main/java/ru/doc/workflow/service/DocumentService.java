package ru.doc.workflow.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.doc.workflow.dto.document.DocumentRequest;
import ru.doc.workflow.dto.document.DocumentResponse;
import ru.doc.workflow.dto.history.DocumentWithHistoryResponse;
import ru.doc.workflow.dto.submit.BatchResultItem;


import java.util.List;

public interface DocumentService {

    DocumentResponse create(DocumentRequest request);

    DocumentWithHistoryResponse getById(Long id);

    Page<DocumentResponse> getByIds(List<Long> ids, Pageable pageable);

    List<BatchResultItem> submitBatch(
            List<Long> ids,
            String initiator,
            String comment);
}