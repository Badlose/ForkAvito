package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.IMAGE_NOT_FOUND;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImageNotFoundException extends AvitoParentException {

    public ImageNotFoundException(String s) {
        super(IMAGE_NOT_FOUND, "Image not found by filepath: [%s]".formatted(s));
    }

}