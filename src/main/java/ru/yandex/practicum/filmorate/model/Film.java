package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Film implements Comparable<Film> {
    private Long id;
    @NotBlank(message = "name cannot be empty")
    private String name;
    @Size(min = 1, max = 200, message = "The value of the field or property must be an integer value lower than or equal to the number in the value element.")
    private String description;
    @MinimumDate(message = "The date cannot be earlier than 1895-12-28")
    private LocalDate releaseDate;
    @Min(value = 1, message = "duration can't be less than 1")
    private int duration;
    private Set<Long> likes = new HashSet<>();

    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }
}