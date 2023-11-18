package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.LikesStorage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

@Component
@Slf4j
public class FilmDbStorage implements FilmStorage, LikesStorage {

    private final JdbcTemplate jdbcTemplate;

    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Film create(Film film) {
        jdbcTemplate.update("insert into films " +
                        "values (?,?,?,?,?,?,?)",
            film.getId(), film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()),
            film.getRate(),film.getDuration(), film.getMpa().getId());
        for (Genre genre : film.getGenres()) {
            jdbcTemplate.update("insert into film_genres values (?,?)", film.getId(), genre.getId());
        }
        log.info("a new film has been added {}", film);
        return film;
    }

    @Override
    public Film update(Film film) {
        Film filmOld = getFilm(film.getId());
        int userRows = jdbcTemplate.update("update films set name = ?, description = ?, " +
                        "release_date = ?, rate = ?, duration = ?, mpa_id = ? where id = ?",
                film.getName(), film.getDescription(), Date.valueOf(film.getReleaseDate()),
                film.getRate(),film.getDuration(), film.getMpa().getId(), film.getId());
            for (Genre genre : film.getGenres()) {
                List<Boolean> empty = jdbcTemplate.query("select * from film_genres" +
                        " where films_id = ? and genre_id = ?", FilmDbStorage::checkingForEmptiness, film.getId(), genre.getId());
                if (empty.size() == 0){
                    jdbcTemplate.update("insert into film_genres values (?,?)", film.getId(), genre.getId());
                }
            }
            for (Genre genre : filmOld.getGenres()) {
                if (!film.getGenres().contains(genre)){
                    jdbcTemplate.update("delete from film_genres where films_id = ? and genre_id = ?", film.getId(), genre.getId());
                }
            }
        log.info("userRows {}", userRows);
        if (userRows == 0){
            return null;
        }
        return getFilm(film.getId());
    }

    @Override
    public List<Film> getAllFilms() {
        List<Film> films = jdbcTemplate.query("select a.*, d.name as mpa_name  from films as a " +
                    "join mpa as d ON a.mpa_id = d.id where a.mpa_id = d.id", FilmDbStorage::createFilm);
        for (Film film : films){
            SqlRowSet userRows = jdbcTemplate.queryForRowSet("select c.* from film_genres as b " +
                    "join genre as c ON b.genre_id = c.id where b.films_id = ?", film.getId());
            while (userRows.next()){
                film.getGenres().add(Genre.builder()
                        .id(userRows.getLong("id"))
                        .name(userRows.getString("name"))
                        .build());
            }
        }
        return films;
    }

    @Override
    public Film getFilm(Long id) {
        List<Film> films = jdbcTemplate.query("select a.*, d.name as mpa_name  from films as a " +
                "join mpa as d ON a.mpa_id = d.id where a.mpa_id = d.id and a.id = ?", FilmDbStorage::createFilm, id);
        if (films.size() != 1){
            throw new FilmNotFoundException("id не найден");
        }
            SqlRowSet userRows = jdbcTemplate.queryForRowSet("select c.* from film_genres as b " +
                    "join genre as c ON b.genre_id = c.id where b.films_id = ?", films.get(0).getId());
            while (userRows.next()){
                films.get(0).getGenres().add(Genre.builder()
                        .id(userRows.getLong("id"))
                        .name(userRows.getString("name"))
                        .build());
            }
        return films.get(0);
    }

    @Override
    public void addLike(Long id, Long filmId) {
        jdbcTemplate.update("insert into likes values (?,?)", id, filmId);
        jdbcTemplate.update("update films set rate = rate+1 where id = ?", filmId);
    }

    @Override
    public void deleteLike(Long id, Long filmId) {
        jdbcTemplate.update("delete from likes where user_id = ? and films_id = ?", id, filmId);
        jdbcTemplate.update("update films set rate = rate-1 where id = ?", filmId);
    }

    @Override
    public List<Film> getBestFilms(int count) {
        List<Film> films = jdbcTemplate.query("select a.*, d.name as mpa_name  from films as a " +
                "join mpa as d ON a.mpa_id = d.id where a.mpa_id = d.id order by rate DESC limit ?", FilmDbStorage::createFilm, count);
        for (Film film : films){
            SqlRowSet userRows = jdbcTemplate.queryForRowSet("select c.* from film_genres as b " +
                    "join genre as c ON b.genre_id = c.id where b.films_id = ?", film.getId());
            while (userRows.next()){
                film.getGenres().add(Genre.builder()
                        .id(userRows.getLong("id"))
                        .name(userRows.getString("name"))
                        .build());
            }
        }
        return films;
    }

    private static Film createFilm(ResultSet rs, int rowNow) throws SQLException {
        return  Film.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .description(rs.getString("description"))
                .releaseDate(rs.getDate("release_date").toLocalDate())
                .duration(rs.getInt("duration"))
                .rate(rs.getLong("rate"))
                .mpa(Mpa.builder()
                        .id(rs.getLong("mpa_id"))
                        .name(rs.getString("mpa_name"))
                        .build())
                .genres(new LinkedHashSet<>())
                .likes(new HashSet<>())
                .build();
    }

    private static boolean checkingForEmptiness(ResultSet rs, int rowNow) throws SQLException {
        return rs.next();
    }

}
