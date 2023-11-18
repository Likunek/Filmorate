package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import net.minidev.json.annotate.JsonIgnore;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.*;

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
    @JsonIgnore
    private long rate = 0;
    @NotNull
    private Mpa mpa;
    private LinkedHashSet<Genre> genres = new LinkedHashSet<>() ;
    private Set<Long> likes = new HashSet<>();
    @Override
    public int compareTo(Film o) {
        return o.getLikes().size() - this.getLikes().size();
    }

    public void addLike(long userId){
        likes.add(userId);
        rate = likes.size();
    }

    public void removeLike(long userId){
        likes.remove(userId);
        rate = likes.size();
    }

}

