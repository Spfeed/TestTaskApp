package com.artem.task.validation;

import com.artem.task.controller.MovieCastController;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.model.MovieCast;
import com.artem.task.service.MovieCastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieCastController.class)
@Import(GlobalExceptionHandler.class)
public class MovieCastControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieCastService movieCastService;

    //Создание персонажа с неверными значениями полей
    @Test
    public void testValidationForMovieCastCreate() throws Exception {
        MovieCast movieCast = new MovieCast(null, 1L, "Имя персонажа");
        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.movieId")
                        .value("Id фильма не должно быть пустым"));
        movieCast.setMovieId(1L);
        movieCast.setActorId(null);
        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.actorId")
                        .value("Id актера не должно быть пустым"));

        movieCast.setActorId(1L);
        movieCast.setCharacterName("");
        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.characterName")
                        .value("Имя персонажа должно быть длиной от 1 до 255 символов"));
    }
    //Редактирование пресонажа с неверными значениями полей
    @Test
    public void testValidationForMovieCastUpdate() throws Exception {
        MovieCast movieCast = new MovieCast(null, 1L, "Имя персонажа");
        mockMvc.perform(put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.movieId")
                        .value("Id фильма не должно быть пустым"));
        movieCast.setMovieId(1L);
        movieCast.setActorId(null);
        mockMvc.perform(put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.actorId")
                        .value("Id актера не должно быть пустым"));

        movieCast.setActorId(1L);
        movieCast.setCharacterName("");
        mockMvc.perform(put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.characterName")
                        .value("Имя персонажа должно быть длиной от 1 до 255 символов"));
    }
}
