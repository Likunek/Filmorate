package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;

import javax.validation.Valid;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    InMemoryFilmStorage inMemoryFilmStorage;
    FilmService filmService;

    @Autowired
    public FilmController(InMemoryFilmStorage inMemoryFilmStorage, FilmService filmService) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.filmService = filmService;
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.create(film);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        return inMemoryFilmStorage.update(film);
    }

    @GetMapping
    public List<Film> getAll() {
        return inMemoryFilmStorage.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilm(@PathVariable String id) {
        return filmService.getFilm(Long.parseLong(id));
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable String id, @PathVariable String userId) {
        return filmService.addLike(Long.parseLong(userId), Long.parseLong(id));
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable String id, @PathVariable String userId) {
        filmService.deleteLike(Long.parseLong(userId), Long.parseLong(id));
    }

    @GetMapping("/popular")
    public List<Film> getBestFilms(@RequestParam(defaultValue = "10") String count) {
        return filmService.getBestFilms(count);
    }
}















