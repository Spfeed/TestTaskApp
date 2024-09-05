package com.artem.task.validation;

import com.artem.task.controller.GenreController;
import com.artem.task.dto.GenreDTO;
import com.artem.task.dto.GenreUpdateDTO;
import com.artem.task.exception.GlobalExceptionHandler;
import com.artem.task.service.GenreService;
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

@WebMvcTest(GenreController.class)
@Import(GlobalExceptionHandler.class)
public class GenreControllerValidationTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private GenreService genreService;

    //Создание жанра с некорректным названием
    @Test
    public void testValidationForGenreCreate() throws Exception {
        GenreDTO genreDTO = new GenreDTO("");
        mockMvc.perform(post("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Название жанра должно быть длиной от 2 до 255 символов"));
    }
    //Редактирование жанра с неверными значениями полей
    @Test
    public void testValidationForGenreUpdate() throws Exception {
        GenreUpdateDTO genreUpdateDTO = new GenreUpdateDTO(null, "Название");
        mockMvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id")
                        .value("Id жанра не должно быть пустым"));

        genreUpdateDTO.setId(1L);
        genreUpdateDTO.setName("");
        mockMvc.perform(put("/genres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(genreUpdateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.name")
                        .value("Название жанра должно быть длиной от 2 до 255 символов"));
    }
}
