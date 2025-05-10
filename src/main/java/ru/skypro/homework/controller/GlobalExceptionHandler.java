package ru.skypro.homework.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.skypro.homework.exception.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessNotAllowedException.class)
    public ResponseEntity<String> handleAccessNotAllowedException(AccessNotAllowedException e) {

//        Map<String, Object> body = new LinkedHashMap<>(); todo вот так надо делать?
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", ex.getMessage());
//        body.put("status", HttpStatus.NOT_FOUND.value());
//        body.put("error", HttpStatus.NOT_FOUND.getReasonPhrase());

//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(AdNotFoundException.class)
    public ResponseEntity<String> handleAdNotFoundException(AdNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<String> handleCommentNotFoundException(CommentNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ImageUploadException.class)
    public ResponseEntity<String> handleImageUploadException(ImageUploadException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); //todo или не нот фаунд
    }

    @ExceptionHandler(IncorrectImageException.class)
    public ResponseEntity<String> handleIncorrectImageException(IncorrectImageException e) { //todo нет применения
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(IncorrectMediaTypeException.class)
    public ResponseEntity<String> handleIncorrectMediaTypeException(IncorrectMediaTypeException e) { //todo нет применения
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(NoSuchAdException.class)
    public ResponseEntity<String> handleNoSuchAdException(NoSuchAdException e) { //todo нет применения
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(UnreadableImageException.class)
    public ResponseEntity<String> handleUnreadableImageException(UnreadableImageException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException(UserAlreadyExistException e) { //todo нет применения
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
