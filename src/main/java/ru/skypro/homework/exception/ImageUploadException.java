package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.PICTURE_SAVING_ERROR;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ImageUploadException extends AvitoParentException {

    public ImageUploadException(String s) {
        super(PICTURE_SAVING_ERROR, "Error saving the picture: [%s]".formatted(s));
    }

}

