package com.example.eventservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ApplicationNotValidDataException extends RuntimeException {
    private final transient String exceptionMessage;
    private final transient Object object;

    public ApplicationNotValidDataException(String exceptionMessage, Object object) {
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