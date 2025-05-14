package ru.skypro.homework.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exception.AvitoParentException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<String> handleAvitoParentException(AvitoParentException e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        return ResponseEntity.status(responseStatus.code()).body(e.getMessage());
    }

}
