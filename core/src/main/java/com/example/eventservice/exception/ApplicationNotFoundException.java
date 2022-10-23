package com.example.eventservice.exception;

public class ApplicationNotFoundException extends RuntimeException {
    private final transient String exceptionMessage;
    private final transient Object object;

    public ApplicationNotFoundException(String exceptionMessage, Object object) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
        this.object = object;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public Object getObject() {
        return object;
    }
}