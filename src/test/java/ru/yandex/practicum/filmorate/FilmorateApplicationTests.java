package ru.yandex.practicum.filmorate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.Versioned;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilmorateApplicationTests {
//	Gson json = new Gson();
//	ObjectMapper objectMapper = new ObjectMapper();
//	HttpClient client = HttpClient.newHttpClient();
//
//	@Test
//    void FilmNameisEmpty() throws JsonProcessingException {
//		URI uri = URI.create("http://localhost:8080/fiLms/newFilm");
//		Film film = new Film("","a b c", LocalDateTime.of(2000,1,23,0,0), 140);
//		HttpRequest httpRequest = HttpRequest.newBuilder()
//				.POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(film)))
//				.uri(uri)
//				.header("Accept","application/json")
//				.build();
//		try {
//			HttpResponse<String> response = client.send(httpRequest,HttpResponse.BodyHandlers.ofString());
//			JsonElement jsonElement = JsonParser.parseString(response.body());
//			JsonObject jsonObject = jsonElement.getAsJsonObject();
//			assertEquals("name cannot be empty", response.body(), "1111");
//		}catch (Exception exception){
//			System.out.println("Во время выполнения программы произошла ошибка");
//		}
//	}
}
