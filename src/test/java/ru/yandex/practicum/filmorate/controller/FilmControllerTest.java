package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class FilmControllerTest  extends Validator<Film> {

    public static final String PATH = "/films";
    @Autowired(required = true)
    private MockMvc mockMvc;


    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(getContentFromFile("controller/request/film.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        getContentFromFile("controller/response/testFilm.json")));
    }

    @Test
    void putNameEmpty() {
        Film film = Film.builder()
                .name("")
                .description("description")
                .releaseDate(LocalDate.of(1910, 1, 1))
                .duration(100)
                .build();

        Assertions.assertEquals("name cannot be empty", validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void putInvalidDate() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1800, 1, 1))
                .duration(100)
                .build();

        Assertions.assertEquals("The date cannot be earlier than 1895-12-28", validateAndGetFirstMessageTemplate(film));
    }

    @Test
    void putNegativeDuration() {
        Film film = Film.builder()
                .name("name")
                .description("description")
                .releaseDate(LocalDate.of(1910, 1, 1))
                .duration(-100)
                .build();

        Assertions.assertEquals("duration can't be less than 1", validateAndGetFirstMessageTemplate(film));
    }

    private String getContentFromFile(String filename){

        try {
            return Files.readString(ResourceUtils.getFile("classpath:" + filename).toPath(),
                    StandardCharsets.UTF_8);
        }catch (IOException exception){
            return "";
        }
    }

}