package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;


public interface UserStorage {

    public User getUser(Long id);
    public User create(User user);
    public  User update(User user);
    public List<User> getAll();
}
