package ru.yandex.practicum.filmorate.exception;

public class InvalidEmailException extends Exception{
    public InvalidEmailException(final String message) {
        super(message);
    }

}
