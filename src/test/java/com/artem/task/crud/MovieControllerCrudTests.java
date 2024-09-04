package com.artem.task.crud;

import com.artem.task.controller.MovieController;
import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(GlobalExceptionHandler.class)
public class MovieControllerCrudTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieService movieService;

    //Поиск несуществующего фильма
    @Test
    public void testGetNonExistMovie() throws Exception {
        Long nonExistMovieId = 999L;
        Mockito.when(movieService.getMovieById(nonExistMovieId))
                        .thenThrow(new EntityNotFoundException("Фильм", nonExistMovieId));
        mockMvc.perform(get("/movies/{id}", nonExistMovieId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Фильм с ключом '999' не найден."));
    }
    //Создание уже существующего фильма
    @Test
    public void testCreateMovieWithDuplicateTitle() throws Exception {
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO(
                "Название фильма", 2024,
                "Описание фильма", 1L, new BigDecimal("8.5"));

        // Настройка мока на выброс исключения при вызове
        Mockito.doThrow(new EntityAlreadyExistsException("Фильм", movieCreateDTO.getTitle()))
                .when(movieService).saveMovie(Mockito.refEq(movieCreateDTO));

        // Попытка создать фильм с дубликатом названия
        mockMvc.perform(post("/movies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(movieCreateDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Фильм с ключом 'Название фильма' уже существует."));
    }

    //Редактирование несуществующего фильма
    @Test
    public void testUpdateNonExistMovie() throws Exception{
        MovieUpdateDTO movieUpdateDTO = new MovieUpdateDTO(
                999L, "Обновленное название", 2024,
                "Обновленное описание", 1L, new BigDecimal("8.5")
        );

        Mockito.doThrow(new EntityNotFoundException("Фильм", movieUpdateDTO.getId()))
                        .when(movieService).updateMovie(Mockito.refEq(movieUpdateDTO));

        mockMvc.perform(put("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movieUpdateDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Фильм с ключом '999' не найден."));
    }
    //Удаление несуществующего фильма
    @Test
    public void testDeleteNonExistMovie() throws Exception {
        Long nonExistMovieId = 999L;

        Mockito.doThrow(new EntityNotFoundException("Фильм", nonExistMovieId))
                        .when(movieService).deleteMovie(nonExistMovieId);

        mockMvc.perform(delete("/movies/{id}", nonExistMovieId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Фильм с ключом '999' не найден."));
    }
}
