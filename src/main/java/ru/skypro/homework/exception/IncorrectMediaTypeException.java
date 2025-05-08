package ru.skypro.homework.exception;
//todo вот так же тоже можно?
public class IncorrectMediaTypeException extends RuntimeException {
    public IncorrectMediaTypeException() {
        System.out.println("Incorrect MediaType");
    }
}
