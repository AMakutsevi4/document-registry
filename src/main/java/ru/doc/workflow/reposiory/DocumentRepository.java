package ru.doc.workflow.reposiory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.doc.workflow.entity.Document;

import java.util.Collection;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @EntityGraph(attributePaths = {"history"})
    Optional<Document> findWithHistoryById(Long id);

    Page<Document> findAllByIdIn(Collection<Long> ids, Pageable pageable);
}
