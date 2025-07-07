package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exception.AvitoParentException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler()
    public ResponseEntity<String> handleAvitoParentException(AvitoParentException e) {
        ResponseStatus responseStatus = e.getClass().getAnnotation(ResponseStatus.class);
        log.error("[error]   Exception occurred: {}", e.getMessage(), e);
        return ResponseEntity.status(responseStatus.code()).body(e.getMessage());
    }

}
