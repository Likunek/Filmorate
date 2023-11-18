package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public Film getFilm(Long id);
    public Film create(Film film);
    public  Film update(Film film);
    public List<Film> getAllFilms();
}
