package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface LikesStorage {

    public void addLike(Long id, Long filmId);
    public void deleteLike(Long id, Long filmId);
    public List<Film> getBestFilms(int count);
}
