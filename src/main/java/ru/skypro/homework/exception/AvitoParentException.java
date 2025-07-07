package ru.skypro.homework.exception;

import lombok.Getter;
import ru.skypro.homework.exception.enums.ErrorType;

@Getter
public class AvitoParentException extends RuntimeException {

    private final ErrorType errorType;

    public AvitoParentException(ErrorType errorType, String message) {
        super(message);
        this.errorType = errorType;
    }

}
