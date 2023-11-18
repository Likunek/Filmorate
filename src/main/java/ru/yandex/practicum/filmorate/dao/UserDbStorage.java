package ru.yandex.practicum.filmorate.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendsStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class UserDbStorage implements UserStorage, FriendsStorage {

    private final JdbcTemplate jdbcTemplate;

    public UserDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public User create(User user) {
        jdbcTemplate.update("insert into users values (?,?,?,?,?)",
                user.getId(), user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()));
        return user;
    }

    @Override
    public User update(User user) {
        int userRows = jdbcTemplate.update("update users set email = ?, login = ?, name = ?, birthday = ? where id = ?",
                user.getEmail(), user.getLogin(), user.getName(), Date.valueOf(user.getBirthday()), user.getId());
        log.info("userRows {}", userRows);
        if (userRows == 0){
            user = null;
        }
        return user;
    }

    @Override
    public User getUser(Long id) {
        List<User> users =  jdbcTemplate.query("select * from users where id = ?", UserDbStorage::createUser, id);
        if (users.size() != 1){
            throw new FilmNotFoundException("id не найден");
        }
        return  users.get(0);
    }

    @Override
    public List<User> getAll() {
        return jdbcTemplate.query("select * from users", UserDbStorage::createUser);
    }

    @Override
    public void addFriend(Long id, Long friendId) {
        int userRows = jdbcTemplate.update("update friends set status = true " +
                "where user_id = ? and friends_id = ?", id, friendId);
        if (userRows == 0){
            jdbcTemplate.update("insert into friends values (?,?, true)",id, friendId);
            jdbcTemplate.update("insert into friends values (?,?, false)",friendId, id);
        }
    }

    @Override
    public void deleteFriend(Long id, Long friendId) {
        jdbcTemplate.update("delete from friends where user_id = ? and friends_id = ?", id, friendId);
        jdbcTemplate.update("delete from friends where user_id = ? and friends_id = ?", friendId, id);
    }

    @Override
    public List<User> getAllFriends(Long id) {
        return jdbcTemplate.query("select * from users where id in " +
                "(select friends_id from friends where user_id = ? and status = true)", UserDbStorage::createUser, id);
    }

    @Override
    public Set<User> getCommonFriends(Long id, Long friendId){
        Set<User> commonFriends = new HashSet<>();
        List<User> userIsFriends = jdbcTemplate.query("select * from users where id in " +
                "(select friends_id from friends where user_id = ? and status = true)", UserDbStorage::createUser, id);
        List<User> friendsOfFriend = jdbcTemplate.query("select * from users where id in " +
                "(select friends_id from friends where user_id = ? and status = true)", UserDbStorage::createUser, friendId);
        for (User i : userIsFriends) {
            if (friendsOfFriend.contains(i)) {
                commonFriends.add(i);
            }
        }
        return commonFriends;
    }

    private static User createUser(ResultSet rs, int rowNow) throws SQLException {
        return User.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .birthday(rs.getDate("birthday").toLocalDate())
                .build();
    }
}
