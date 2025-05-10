package ru.skypro.homework.exception;

import java.io.IOException;

public class ImageUploadException extends RuntimeException {
    public ImageUploadException(String message, Throwable cause) {
        super(message, cause);
    }
}
