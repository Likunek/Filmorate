package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;


public interface UserStorage {

    public User create(User user);
    public  User update(User user);
    public List<User> getAll();
}
