package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Genre> getAllGenre() {
        return jdbcTemplate.query("select * from genre", GenreDbStorage::createGenre);
    }

    @Override
    public Genre getGenre(Long id) {
        List<Genre> genres = jdbcTemplate.query("select * from genre where id = ?", GenreDbStorage::createGenre, id);
        if (genres.size() != 1){
            throw new DataNotFoundException(String.format("genre with id %s not single", id));
        }
        return genres.get(0);
    }

    static Genre createGenre(ResultSet rs, int rowNow) throws SQLException {
        return Genre.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
