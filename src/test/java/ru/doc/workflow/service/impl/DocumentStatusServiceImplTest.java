package ru.doc.workflow.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import ru.doc.workflow.controller.dto.document.DocumentRequest;
import ru.doc.workflow.controller.dto.document.DocumentResponse;
import ru.doc.workflow.controller.dto.submit.BatchResultItem;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.BatchStatus;
import ru.doc.workflow.enums.DocumentStatus;
import ru.doc.workflow.reposiory.ApprovalRegistryRepository;
import ru.doc.workflow.reposiory.DocumentRepository;
import ru.doc.workflow.sheduller.DocumentBatchWorkers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
@DisplayName("Тесты бизнес-логики документа")
class DocumentServiceImplTest {

    @Autowired
    private DocumentServiceImpl documentService;

    @Autowired
    private DocumentRepository documentRepository;

    @MockitoSpyBean
    private ApprovalRegistryRepository registryRepository;

    @MockitoBean
    private DocumentBatchWorkers batchWorkers;

    @BeforeEach
    void setUp() {
        registryRepository.deleteAll();
        documentRepository.deleteAll();
    }

    @Test
    @DisplayName("Создание одного документа")
    void create_happyPath() {
        DocumentRequest request = new DocumentRequest("Ivanov", "Title 1", "User-1");

        DocumentResponse response = documentService.create(request);

        assertNotNull(response.id());
        assertTrue(documentRepository.existsById(response.id()));
    }

    @Test
    @DisplayName("Пакетный submit")
    void submitBatch_success() {
        Long id1 = createTestDocument("DOC-1", DocumentStatus.DRAFT);
        Long id2 = createTestDocument("DOC-2", DocumentStatus.DRAFT);

        List<BatchResultItem> results = documentService.submitBatch(List.of(id1, id2), "Robot", "Comment");

        assertEquals(2, results.size());
        assertTrue(results.stream().allMatch(r -> r.status() == BatchStatus.SUCCESS));
        assertEquals(DocumentStatus.SUBMITTED, documentRepository.findById(id1).orElseThrow().getStatus());
        assertEquals(DocumentStatus.SUBMITTED, documentRepository.findById(id2).orElseThrow().getStatus());
    }

    @Test
    @DisplayName("Пакетный approve")
    void approveBatch_partialResults() {
        Long idSuccess = createTestDocument("DOC-OK", DocumentStatus.SUBMITTED);
        Long idConflict = createTestDocument("DOC-BAD", DocumentStatus.DRAFT);
        Long idNotFound = 9999L;

        List<BatchResultItem> results = documentService.approveBatch(
                List.of(idSuccess, idConflict, idNotFound), "Boss", "Final");

        assertEquals(3, results.size());
        assertTrue(results.stream().anyMatch(r -> r.id().equals(idSuccess) && r.status() == BatchStatus.SUCCESS));
        assertTrue(results.stream().anyMatch(r -> r.id().equals(idConflict) && r.status() == BatchStatus.CONFLICT));
        assertTrue(results.stream().anyMatch(r -> r.id().equals(idNotFound) && r.status() == BatchStatus.NOT_FOUND));
    }

    @Test
    @DisplayName("Откат approve")
    void approve_rollbackOnRegistryError() {
        Long id = createTestDocument("DOC-ROLLBACK", DocumentStatus.SUBMITTED);
        doThrow(new RuntimeException()).when(registryRepository).save(any());
        documentService.approveBatch(List.of(id), "User", "Test");
        Document finalDoc = documentRepository.findById(id).orElseThrow();

        assertEquals(DocumentStatus.SUBMITTED, finalDoc.getStatus());
    }

    private Long createTestDocument(String number, DocumentStatus status) {
        Document doc = new Document();
        doc.setNumber(number);
        doc.setAuthor("Test Author");
        doc.setTitle("Test Title");
        doc.setStatus(status);
        return documentRepository.save(doc).getId();
    }
}