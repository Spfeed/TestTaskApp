package com.artem.task.validation;

import com.artem.task.controller.MovieController;
import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(GlobalExceptionHandler.class)
public class MovieControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieService movieService;

    //Создание фильма с неверными значениями полей
    @Test
    public void testValidationForMovieCreate() throws Exception {
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO("", 1889, "Описание",
                1L, new BigDecimal("8.5")); // Неверный год

        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title")
                        .value("Название фильма должно быть длиной от 1 до 255 символов"));

        movieCreateDTO.setTitle("Название фильма");
        movieCreateDTO.setReleaseYear(1887);
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.releaseYear")
                        .value("Год выпуска фильма должен быть не ранее 1888"));

        movieCreateDTO.setReleaseYear(2101);
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.releaseYear")
                        .value("Год выпуска фильма должен быть не позднее 2100"));

        movieCreateDTO.setReleaseYear(2024);
        movieCreateDTO.setDescription("");
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description")
                        .value("Описание фильма не должно быть пустым"));

        movieCreateDTO.setDescription("Описание");
        movieCreateDTO.setGenreId(null);
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.genreId")
                        .value("Жанр должен быть указан"));

        movieCreateDTO.setGenreId(1L);
        movieCreateDTO.setRating(new BigDecimal("-0.1"));
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rating")
                        .value("Рейтинг должен быть больше 0"));

        movieCreateDTO.setRating(new BigDecimal("11.0"));
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rating")
                        .value("Рейтинг должен быть не более 10"));
    }
    //Редактирование фильма с неверными значениями полей
    @Test
    public void testValidationForMovieUpdate() throws Exception{
        MovieUpdateDTO movieUpdateDTO = new MovieUpdateDTO(null, "Название", 2024,
                "Описание", 1L, new BigDecimal("8.5"));
        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id").value("Id фильма не должно быть пустым"));

        movieUpdateDTO.setId(1L);
        movieUpdateDTO.setTitle("");
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title")
                        .value("Название фильма должно быть длиной от 1 до 255 символов"));

        movieUpdateDTO.setTitle("Название");
        movieUpdateDTO.setReleaseYear(1887);
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.releaseYear")
                        .value("Год выпуска фильма должен быть не ранее 1888"));

        movieUpdateDTO.setReleaseYear(2101);
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.releaseYear")
                        .value("Год выпуска фильма должен быть не позднее 2100"));

        movieUpdateDTO.setReleaseYear(2024);
        movieUpdateDTO.setDescription("");
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description")
                        .value("Описание фильма не должно быть пустым"));

        movieUpdateDTO.setDescription("Описание");
        movieUpdateDTO.setGenreId(null);
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.genreId")
                        .value("Жанр должен быть указан"));

        movieUpdateDTO.setGenreId(1L);
        movieUpdateDTO.setRating(new BigDecimal("-0.1"));
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rating")
                        .value("Рейтинг должен быть больше 0"));

        movieUpdateDTO.setRating(new BigDecimal("11.0"));
        mockMvc.perform(put("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.rating")
                        .value("Рейтинг должен быть не более 10"));
    }
}
