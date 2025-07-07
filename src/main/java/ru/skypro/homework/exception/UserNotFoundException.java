package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static ru.skypro.homework.exception.enums.ErrorType.USER_NOT_FOUND_BY_USERNAME;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AvitoParentException {

    public UserNotFoundException(String string) {
        super(USER_NOT_FOUND_BY_USERNAME, "User not found by username: [%s]".formatted(string));
    }

}