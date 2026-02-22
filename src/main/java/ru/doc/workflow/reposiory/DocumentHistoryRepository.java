package ru.doc.workflow.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.doc.workflow.entity.History;

public interface DocumentHistoryRepository extends JpaRepository<History, Long> {
}