package ru.doc.workflow.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import ru.doc.workflow.controller.dto.document.DocumentResponse;
import ru.doc.workflow.controller.dto.history.DocumentWithHistoryResponse;
import ru.doc.workflow.entity.Document;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DocumentMapper {

    DocumentResponse toResponse(Document entity);

    DocumentWithHistoryResponse toResponseWithHistory(Document entity);
}
