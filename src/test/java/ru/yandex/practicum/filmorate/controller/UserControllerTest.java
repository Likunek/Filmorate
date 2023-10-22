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
import org.springframework.util.ResourceUtils;
import ru.yandex.practicum.filmorate.model.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.time.LocalDate;


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest  extends Validator<User> {

    public static final String PATH = "/users";
    @Autowired(required = true)
    private MockMvc mockMvc;


    @Test
    void create() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getContentFromFile("controller/request/user.json")))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        getContentFromFile("controller/response/testUser.json")));
    }

    @Test
    void putEmailEmpty() {
        User user = User.builder()
                .email("lout")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        Assertions.assertEquals("Email should be valid", validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void putLoginEmpty() {
        User user = User.builder()
                .email("lika@gmail.com")
                .login("")
                .name("name")
                .birthday(LocalDate.of(2000, 1, 1))
                .build();

        Assertions.assertEquals("login cannot be empty", validateAndGetFirstMessageTemplate(user));
    }

    @Test
    void putInvalidDate() {
        User user = User.builder()
                .email("lika@gmail.com")
                .login("login")
                .name("name")
                .birthday(LocalDate.of(2024, 1, 1))
                .build();

        Assertions.assertEquals("wrong birthday date", validateAndGetFirstMessageTemplate(user));
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