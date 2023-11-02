package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;



@Service
@Slf4j
public class UserService {

    UserStorage userStorage;

    @Autowired
    public UserService(InMemoryUserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User getUser(Long id) {
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                log.info("get user: {}", user);
                return user;
            }
        }
            throw  new UserNotFoundException("id не найден");
    }

    public User addFriend(Long id, Long friendId) {
        byte actualId = 0;
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {actualId++;}
            if (user.getId().equals(friendId)){actualId++;}
        }
        if (actualId == 2){
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                user.getFriends().add(friendId);
                log.info("add friend1: {}", user);
            }
            if (user.getId().equals(friendId)) {
                user.getFriends().add(id);
                log.info("add friend2: {}", user);
                return user;
            }
        }
        } else {
            throw new UserNotFoundException("id не найден");
        }
        throw new UserNotFoundException("id не найден");
    }

    public void deleteFriend(Long id, Long friendId) {

        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                user.getFriends().remove(friendId);
                log.info("delete user's friend: {}", user);
            }
            if (user.getId().equals(friendId)) {
                user.getFriends().remove(id);
                log.info("delete friend's user: {}", user);
            }
        }
    }

    public Set<User> getAllFriends(Long id) {
        Set<Long> friendsId = null;
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                log.info("id friends: {}", user.getFriends());
                friendsId =  user.getFriends();
            }
        }
        if (friendsId != null){
            return friendsId.stream()
                    .map(this::getUser)
                    .collect(Collectors.toSet());
        }
        throw new UserNotFoundException("id не найден");
    }

    public Set<User> getCommonFriends(Long id, Long friendId) {
        Set<Long> userFriends = null;
        Set<Long> friendsOfFriend = null;
        Set<Long> commonFriendsId = new HashSet<>();
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                userFriends = user.getFriends();
            }
            if (user.getId().equals(friendId)) {
                friendsOfFriend = user.getFriends();
            }
        }
        if (userFriends != null && friendsOfFriend != null) {
            for (Long i : userFriends) {
                if (friendsOfFriend.contains(i)) {
                    commonFriendsId.add(i);
                }
            }
            log.info("common friends: {}", commonFriendsId);
            return commonFriendsId.stream()
                    .map(this::getUser)
                    .collect(Collectors.toSet());
        }else {
            throw new UserNotFoundException("id не найден");
        }
    }

}






















