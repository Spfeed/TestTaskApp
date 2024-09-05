package com.artem.task.crud;

import com.artem.task.controller.GenreController;
import com.artem.task.dto.GenreDTO;
import com.artem.task.dto.GenreUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.GenreService;
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

@WebMvcTest(GenreController.class)
@Import(GlobalExceptionHandler.class)
public class GenreControllerCrudTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GenreService genreService;

    //Поиск несуществющего жанра
    @Test
    public void testGetNonExistGenre() throws Exception {
        Long nonExistGenreId = 999L;
        Mockito.when(genreService.getGenreById(nonExistGenreId))
                .thenThrow(new EntityNotFoundException("Жанр", nonExistGenreId));
        mockMvc.perform(get("/genres/{id}", nonExistGenreId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Жанр с ключом '999' не найден."));
    }

    //Создание существующего жанра
    @Test
    public void testCreateGenreAlreadyExists() throws Exception {
        GenreDTO genreDTO = new GenreDTO("Название жанра");
        Mockito.doThrow(new EntityAlreadyExistsException("Жанр", genreDTO.getName()))
                .when(genreService).saveGenre(Mockito.refEq(genreDTO));

        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isConflict())
                .andExpect(content().string("Жанр с ключом 'Название жанра' уже существует."));
    }
    //Редактирование несуществующего жанра
    @Test
    public void testUpdateGenreNotFound() throws Exception {
        GenreUpdateDTO genreUpdateDTO = new GenreUpdateDTO(999L, "Название жанра");

        Mockito.doThrow(new EntityNotFoundException("Жанр", genreUpdateDTO.getId()))
                .when(genreService).updateGenre(Mockito.refEq(genreUpdateDTO));

        mockMvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreUpdateDTO)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Жанр с ключом '999' не найден."));
    }
    //Удаление несуществующего жанра
    @Test
    public void testDeleteGenreNotFound() throws Exception {
        Long nonExistGenreId = 999L;
        Mockito.doThrow(new EntityNotFoundException("Жанр", nonExistGenreId))
                .when(genreService).deleteGenre(nonExistGenreId);

        mockMvc.perform(delete("/genres/{id}", nonExistGenreId))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Жанр с ключом '999' не найден."));
    }
}
