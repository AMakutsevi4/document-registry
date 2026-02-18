package ru.doc.workflow.service;

public interface DocumentStatusService {

    void submit(Long id, String initiator, String comment);
}


