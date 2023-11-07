package ru.yandex.practicum.filmorate.model;

public class FriendShip {
    public Long friendId;
    public boolean status;

    public FriendShip(Long friendId, boolean status) {
        this.friendId = friendId;
        this.status = status;
    }
}
