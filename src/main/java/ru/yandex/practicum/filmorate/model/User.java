package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {
    private Long id;
    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "login cannot be empty")
    private String login;
    private String name;
    @Past(message = "wrong birthday date")
    private LocalDate birthday;
    private Set<Long> friends = new HashSet<>();


//    public void addFriends(Long id){
//        friends.add(new Friends(this.id, id, true));
//    }
//
//    public void addAnUnconfirmedFriend(Long id){
//        friends.add(new Friends(this.id, id, false));
//    }
//
//    public void deleteFriend(Long id){
//        friends.removeIf(friend -> friend.friendId.equals(id));
//    }
//
//    public Set<Friends> allFriends(){
//        return friends;
//    }
}