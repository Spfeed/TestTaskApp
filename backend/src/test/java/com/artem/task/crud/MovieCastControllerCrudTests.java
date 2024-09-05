package com.artem.task.crud;

import com.artem.task.controller.MovieCastController;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.exception.MovieCastAlreadyExistsException;
import com.artem.task.exception.MovieCastNotFoundException;
import com.artem.task.model.MovieCast;
import com.artem.task.service.MovieCastService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieCastController.class)
@Import(GlobalExceptionHandler.class)
public class MovieCastControllerCrudTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieCastService movieCastService;

    //Поиск персонажа по несуществующему id фильма и актера
    @Test
    public void testGetNonExistMovieCast() throws Exception {
        Long nonExistMovieId = 1L;
        Long nonExistActorId = 2L;

        Mockito.when(movieCastService.getCharacterByMovieIdAndActorId(nonExistMovieId, nonExistActorId))
                .thenThrow(new MovieCastNotFoundException(nonExistMovieId, nonExistActorId));
        mockMvc.perform(get("/characters/movie/{movieId}/actor/{actorId}", nonExistMovieId, nonExistActorId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Персонаж с ключом 'Id фильма: 1, Id актера: 2' не найден."));
    }
    //Создание существующего персонажа
    @Test
    public void testSaveCharacterAlreadyExists() throws Exception {
        MovieCast movieCast = new MovieCast(1L, 2L, "Персонаж");
        Mockito.doThrow(new MovieCastAlreadyExistsException(movieCast.getMovieId(), movieCast.getActorId()))
                .when(movieCastService).saveCharacter(Mockito.refEq(movieCast));

        mockMvc.perform(post("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Персонаж с ключом 'Id фильма: 1, Id актера: 2' уже существует."));
    }
    //Редактирование несуществующего персонажа
    @Test
    public void testUpdateCharacterNotFound() throws Exception {
        MovieCast movieCast = new MovieCast(1L, 2L, "Hero");
        Mockito.doThrow(new MovieCastNotFoundException(movieCast.getMovieId(), movieCast.getActorId()))
                .when(movieCastService).updateCharacter(Mockito.refEq(movieCast));

        mockMvc.perform(put("/characters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCast)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Персонаж с ключом 'Id фильма: 1, Id актера: 2' не найден."));
    }
    //Удаление несуществующего персонажа
    @Test
    public void testDeleteCharacterNotFound() throws Exception {
        Long movieId = 1L;
        Long actorId = 2L;
        Mockito.doThrow(new MovieCastNotFoundException(movieId, actorId))
                .when(movieCastService).deleteCharacter(movieId, actorId);

        mockMvc.perform(delete("/characters/movie/{movieId}/actor/{actorId}", movieId, actorId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Персонаж с ключом 'Id фильма: 1, Id актера: 2' не найден."));
    }
}
