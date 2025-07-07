package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.AD_ACCESS_NOT_ALLOWED;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AdAccessNotAllowedException extends AvitoParentException {
    public AdAccessNotAllowedException(Integer id) {
        super(AD_ACCESS_NOT_ALLOWED, "You are not authorized to edit this ad: [%s]".formatted(id));
    }

}
