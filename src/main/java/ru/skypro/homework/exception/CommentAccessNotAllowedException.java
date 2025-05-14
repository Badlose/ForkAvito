package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.COMMENT_ACCESS_NOT_ALLOWED;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class CommentAccessNotAllowedException extends AvitoParentException {
    public CommentAccessNotAllowedException(Integer id) {
        super(COMMENT_ACCESS_NOT_ALLOWED, "You are not authorized to edit this comment: [%s]".formatted(id));
    }
}
