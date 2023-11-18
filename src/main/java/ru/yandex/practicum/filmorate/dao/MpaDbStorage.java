package ru.yandex.practicum.filmorate.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("select * from mpa", MpaDbStorage::createMpa);
    }

    @Override
    public Mpa getMpa(Long id) {
        List<Mpa> mpa = jdbcTemplate.query("select * from mpa where id = ?", MpaDbStorage::createMpa, id);
        if (mpa.size() != 1){
            throw new DataNotFoundException(String.format("mpa with id %s not single", id));
        }
        return mpa.get(0);
    }

    static Mpa createMpa(ResultSet rs, int rowNow) throws SQLException {
        return Mpa.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}
