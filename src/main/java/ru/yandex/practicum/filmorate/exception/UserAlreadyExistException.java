package ru.yandex.practicum.filmorate.exception;

public class UserAlreadyExistException extends Exception{

    public UserAlreadyExistException(final String message) {
        super(message);
    }
}
