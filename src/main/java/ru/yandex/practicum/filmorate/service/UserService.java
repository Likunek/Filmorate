package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.UserNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;



@Service
@Slf4j
public class UserService {

    UserStorage userStorage;
    FriendsStorage friendsStorage;
    private long generatedId;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage,  FriendsStorage friendsStorage) {
        this.userStorage = userStorage;
        this.friendsStorage = friendsStorage;
    }

    public User create(User user){
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        user.setId(++generatedId);
        User userNew = userStorage.create(user);
        log.info("a new user has been added {}", user);
        return user;
    }
    public User update(User user){
        User userNew = userStorage.update(user);
        if(userNew!= null){
            log.info("update user {}", userNew);
            return userNew;
        }
        throw new UserNotFoundException("id не найден");
    }

    public User getUser(Long id) {
        User user = userStorage.getUser(id);
        if(user!= null){
            log.info("Get film {}", user);
            return user;
        }
        throw new UserNotFoundException("id не найден");
    }

    public User addFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friendOfUser = userStorage.getUser(friendId);
        if(user!= null && friendOfUser!= null){
            log.info("add friend {}", friendOfUser);
            friendsStorage.addFriend(id, friendId);
            return friendOfUser;
        }
        throw new UserNotFoundException("id не найден");
    }

    public void deleteFriend(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friendOfUser = userStorage.getUser(friendId);
        if(user!= null && friendOfUser!= null){
            log.info("delete friend {}", friendOfUser);
            friendsStorage.deleteFriend(id, friendId);
        }else {
            throw new FilmNotFoundException("id не найден");
        }
    }

    public List<User> getAllFriends(Long id) {
        User user = userStorage.getUser(id);
        if (user != null){
            List<User> friends = friendsStorage.getAllFriends(id);
            log.info("get all friends {}", friends);
            return friends;
        }
        throw new UserNotFoundException("id не найден");
    }

    public Set<User> getCommonFriends(Long id, Long friendId) {
        User user = userStorage.getUser(id);
        User friendOfUser = userStorage.getUser(friendId);
        if(user!= null && friendOfUser!= null){
            Set<User> commonFriends = friendsStorage.getCommonFriends(id, friendId);
            log.info("get all common friends {}", commonFriends);
            return commonFriends;
        }
        throw new UserNotFoundException("id не найден");
    }

}






















