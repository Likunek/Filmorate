package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.BaseUnit;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseController<T extends BaseUnit> {

    private final Map<Long, T> storage = new HashMap<>();
    private long generatedId;

    public T creat(T data){
        data.setId(++generatedId);
        storage.put(data.getId(), data);
        return data;
    }

    public T update(T data) {
        if(!storage.containsKey(data.getId())){
           throw new DataNotFoundException(String.format("Data %s not found", data));
        }
        storage.put(data.getId(), data);
        return data;
    }

    public List<T> getAll(){
        return new ArrayList<>(storage.values());
    }
}
