package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController extends BaseController<User>{

    @PostMapping
    public User create(@Valid @RequestBody User user){
        if (user.getName().isBlank() || user.getName() == null){
            user.setName(user.getLogin());
        }
        log.info("Creating user {}", user);
        return  super.creat(user);
    }

    @PutMapping
    public User update(@Valid @RequestBody User user){
        log.info("Updating user {}", user);
        return  super.update(user);
    }

    @GetMapping
    public List<User> getAll(){
//        log.info("Getting users {}", user);
        return super.getAll();
    }
}