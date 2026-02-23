package ru.doc.workflow.exception;

public class RegistryException extends RuntimeException {
    public RegistryException(String message, Throwable cause) {
        super(message, cause);
    }
}
