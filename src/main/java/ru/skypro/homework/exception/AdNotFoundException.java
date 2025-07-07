package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.AD_NOT_FOUND_BY_ID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AdNotFoundException extends AvitoParentException {

    public AdNotFoundException(Integer id) {
        super(AD_NOT_FOUND_BY_ID, "Ad not found by id: [%s]".formatted(id));
    }

}