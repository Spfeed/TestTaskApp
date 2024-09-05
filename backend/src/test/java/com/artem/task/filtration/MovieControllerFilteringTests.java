package com.artem.task.filtration;

import com.artem.task.controller.MovieController;
import com.artem.task.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
public class MovieControllerFilteringTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MovieService movieService;

    //Фильтрация по несуществующей части названия фильма
    @Test
    public void testFilterMoviesByNonMatchingTitle() throws Exception {
        String nonMatchingKeyword = "НеСуществующаяЧастьНазвания";
        mockMvc.perform(get("/movies/serviceFiltration/{keyword}", nonMatchingKeyword))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
    //Фильтрация по несуществующим жанрам
    @Test
    public void testFilterMoviesByNonExistingGenres() throws Exception {
        List<String> nonExistingGenres = List.of("NonExistingGenre1", "NonExistingGenre2");

        mockMvc.perform(post("/movies/filterByGenres")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nonExistingGenres)))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
    //Фильтрация по несуществующему актеру
    @Test
    public void testFilterMoviesByNonExistingActor() throws Exception {
        String nonExistingActorName = "НеСуществующийАктер";

        mockMvc.perform(get("/movies/filterByActor/{actorName}", nonExistingActorName))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
