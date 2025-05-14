package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.*;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UnreadableImageException extends AvitoParentException {

    public UnreadableImageException(String s) {
        super(INCORRECT_IMAGE, "Incorrect image for id: [%s]".formatted(s));
    }

}