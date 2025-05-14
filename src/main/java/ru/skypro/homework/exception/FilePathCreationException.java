package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.FILE_PATH_NOT_CREATED;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FilePathCreationException extends AvitoParentException {

    public FilePathCreationException(String string) {
        super(FILE_PATH_NOT_CREATED, "Error creating filepath for: [%s]".formatted(string));
    }

}
