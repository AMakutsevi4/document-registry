package ru.doc.workflow.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.doc.workflow.entity.DocumentHistory;

public interface DocumentHistoryRepository extends JpaRepository<DocumentHistory, Long> {
}