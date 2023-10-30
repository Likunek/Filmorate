package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, User> storage = new HashMap<>();
    private long generatedId;

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        log.info("Creating user {}", user);
        user.setId(++generatedId);
        storage.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info("Updating user {}", user);
        if (!storage.containsKey(user.getId())) {
            throw new DataNotFoundException(String.format("Data %s not found", user));
        }
        storage.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAll() {
        return new ArrayList<>(storage.values());
    }
}