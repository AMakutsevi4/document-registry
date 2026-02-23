package ru.doc.workflow.reposiory;

import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.doc.workflow.entity.Document;
import ru.doc.workflow.enums.DocumentStatus;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    @EntityGraph(attributePaths = {"history"})
    Optional<Document> findWithHistoryById(Long id);

    Page<Document> findAllByIdIn(Collection<Long> ids, Pageable pageable);

    @Query("SELECT d.id FROM Document d WHERE d.status = :status ORDER BY d.createdAt ASC")
    List<Long> findIdsByStatus(@Param("status") DocumentStatus status, Limit limit);
}
