package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Service
@Slf4j
public class FilmService {

    FilmStorage filmStorage;
    UserStorage userStorage;
    LikesStorage likesStorage;
    private long generatedId;

    @Autowired
    public FilmService(@Qualifier("filmDbStorage") FilmStorage filmStorage,
                       @Qualifier("userDbStorage") UserStorage userStorage,  LikesStorage likesStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likesStorage = likesStorage;
    }

    public Film create(Film film){
        film.setId(++generatedId);
       Film filmNew = filmStorage.create(film);
        log.info("Creating film {}", filmNew);
        return filmNew;
    }
    public Film update(Film film){
        Film filmNew = filmStorage.update(film);
        if(filmNew!= null){
            log.info("update film {}", filmNew);
            return filmNew;
        }
        throw new FilmNotFoundException("id не найден");
    }


    public Film getFilm(Long id) {
        Film film = filmStorage.getFilm(id);
        if(film!= null){
            log.info("Get film {}", film);
            return film;
        }
        throw new FilmNotFoundException("id не найден");
    }

    public Film addLike(Long id, Long filmId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(id);
        if(film!= null && user!= null){
            log.info("add like {}", film);
            likesStorage.addLike(id, filmId);
            return film;
        }
        throw new FilmNotFoundException("id не найден");
    }

    public void deleteLike(Long id, Long filmId) {
        Film film = filmStorage.getFilm(filmId);
        User user = userStorage.getUser(id);
        if(film!= null && user!= null){
            log.info("delete like {}", film);
            likesStorage.deleteLike(id, filmId);
        }else {
            throw new FilmNotFoundException("id не найден");
        }
    }

    public List<Film> getBestFilms(int count) {
        return likesStorage.getBestFilms(count);
    }
}


























