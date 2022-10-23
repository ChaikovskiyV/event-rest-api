package com.example.eventservice.controller.exceptionhandler;

import com.example.eventservice.exception.ApplicationDuplicateException;
import com.example.eventservice.exception.ApplicationNotFoundException;
import com.example.eventservice.exception.ApplicationNotValidDataException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CustomExceptionDataObject handleNotFoundException(ApplicationNotFoundException e) {
        return new CustomExceptionDataObject(HttpStatus.NOT_FOUND.value(), createMessage(e.getExceptionMessage(), e.getObject()));
    }

    @ExceptionHandler(ApplicationDuplicateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public CustomExceptionDataObject handleDuplicateException(ApplicationDuplicateException e) {
        return new CustomExceptionDataObject(HttpStatus.CONFLICT.value(), createMessage(e.getExceptionMessage(), e.getObject()));
    }

    @ExceptionHandler(ApplicationNotValidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CustomExceptionDataObject handleNotValidDataException(ApplicationNotValidDataException e) {
        String errorMessage;

        if(e.getObject() instanceof BindingResult bindingResult) {
            errorMessage = createBindingResultMessage(bindingResult);
        } else {
            errorMessage = createMessage(e.getExceptionMessage(), e.getObject());
        }

        return new CustomExceptionDataObject(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CustomExceptionDataObject handleInternalServerException(Exception e) {
        return new CustomExceptionDataObject(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    private String createMessage(String errorMessage, Object wrongObject) {
        return String.format(errorMessage, wrongObject.toString());
    }

    private String createBindingResultMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce("", (s, str) -> String.join(" ", s, str));
    }
}