package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Set;

public interface FriendsStorage {

    public void addFriend(Long id, Long friendId);
    public void deleteFriend(Long id, Long friendId);
    public List<User> getAllFriends(Long id);
    public Set<User> getCommonFriends(Long id, Long friendId);
}
