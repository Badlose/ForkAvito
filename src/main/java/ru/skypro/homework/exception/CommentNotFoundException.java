package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.*;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CommentNotFoundException extends AvitoParentException {

    public CommentNotFoundException(Integer id) {
        super(COMMENT_NOT_FOUND_BY_ID, "Comment not found by id: [%s]".formatted(id));
    }

}
