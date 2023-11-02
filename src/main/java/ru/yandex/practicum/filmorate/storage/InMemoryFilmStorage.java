package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> storage = new HashMap<>();
    private long generatedId;

    @Override
    public Film create(Film film) {
        log.info("Creating film {}", film);
        film.setId(++generatedId);
        storage.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!storage.containsKey(film.getId())) {
            throw new DataNotFoundException(String.format("Data %s not found", film));
        }
        log.info("Updating film {}", film);
        storage.put(film.getId(), film);
        return film;
    }

    @Override
    public List<Film> getAll() {
        return new ArrayList<>(storage.values());
    }
}
