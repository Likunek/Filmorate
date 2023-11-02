package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {

    FilmStorage filmStorage;
    UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public Film getFilm(Long id) {
        for (Film film : filmStorage.getAll()) {
            if (film.getId().equals(id)) {
                log.info("get film: {}", film);
                return film;
            }
        }
        throw new FilmNotFoundException("id не найден");
    }

    public Film addLike(Long id, Long filmId) {
        for (Film film : filmStorage.getAll()) {
            if (film.getId().equals(filmId)) {
                film.getLikes().add(id);
                log.info("add a like to the movie: {}", film);
                return film;
            }
        }
        throw new FilmNotFoundException("id не найден");
    }

    public void deleteLike(Long id, Long filmId) {
        byte found = 0;
        for (User user : userStorage.getAll()) {
            if (user.getId().equals(id)) {
                log.info("user found: {}", user);
                found++;
            }
        }
        for (Film film : filmStorage.getAll()) {
            if (film.getId().equals(filmId)) {
                log.info("remove a like from a movie: {}", film);
                film.getLikes().remove(id);
                found++;
            }
        }
        if (found != 2) {
            throw new FilmNotFoundException("id не найден");
        }
    }

    public List<Film> getBestFilms(String count) {
        List<Film> films = new ArrayList<>(filmStorage.getAll());
        Collections.sort(films);
        log.info("sort film: {}", films);
        return films.stream()
                .limit(Integer.parseInt(count))
                .collect(Collectors.toList());
    }
}


























