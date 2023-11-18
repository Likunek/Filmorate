package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode(of = {"friendId"})
public class Friends {
    public Long userId;
    public Long friendId;
    public boolean status;

    public Friends(Long userId, Long friendId, boolean status) {
        this.friendId = friendId;
        this.status = status;
        this.userId = userId;
    }
}
