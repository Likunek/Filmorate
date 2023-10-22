package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class User extends BaseUnit {

    @NotEmpty
    @Email(message = "Email should be valid")
    private String email;
    @NotBlank(message = "login cannot be empty")
    private String login;

    private String name;
    @Past(message = "wrong birthday date")
    private LocalDate birthday;

}
