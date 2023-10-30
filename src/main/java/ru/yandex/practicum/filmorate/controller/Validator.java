package ru.yandex.practicum.filmorate.controller;

import javax.validation.Validation;

public class Validator<T> {

    protected static final javax.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    protected <T> String validateAndGetFirstMessageTemplate(T obj) {
        return validator.validate(obj).stream()
                .findFirst()
                .get()
                .getConstraintDescriptor()
                .getMessageTemplate();
    }
}