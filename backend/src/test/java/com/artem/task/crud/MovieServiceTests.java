package com.artem.task.crud;

import com.artem.task.dao.ActorDao;
import com.artem.task.dao.GenreDao;
import com.artem.task.dao.MovieCastDao;
import com.artem.task.dao.MovieDao;
import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.service.MovieService;
import com.artem.task.model.Movie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieServiceTests {

    private MovieService movieService;
    private MovieDao movieDao;
    private GenreDao genreDao;
    private ActorDao actorDao;
    private MovieCastDao movieCastDao;

    @BeforeEach
    void setUp() {
        movieDao = mock(MovieDao.class);
        genreDao = mock(GenreDao.class);
        actorDao = mock(ActorDao.class);
        movieCastDao = mock(MovieCastDao.class);
        movieService = new MovieService(movieDao, genreDao, actorDao, movieCastDao);
    }

    //Поиск по id не дал результатов
    @Test
    void testGetMovieByIdThrowsEntityNotFoundException() {
        Long movieId = 1L;
        when(movieDao.findById(movieId)).thenThrow(new EntityNotFoundException("Фильм", movieId.toString()));
        assertThrows(EntityNotFoundException.class, () -> movieService.getMovieById(movieId));
        verify(movieDao, times(1)).findById(movieId);
    }
    //Попытка создания существующего фильма
    @Test
    void testSaveMovieThrowsEntityAlreadyExistsException() {
        MovieCreateDTO movieCreateDTO = new MovieCreateDTO("Название фильма", 2024,
                "Описание фильма", 1L, new BigDecimal("8.5"));
        doThrow(new EntityAlreadyExistsException("Фильм", movieCreateDTO.getTitle())).when(movieDao)
                .save(any(Movie.class));
        assertThrows(EntityAlreadyExistsException.class, () -> movieService.saveMovie(movieCreateDTO));
        verify(movieDao, times(1)).save(any(Movie.class));
    }
    //Редактирование несуществующего фильма
    @Test
    void testUpdateMovieThrowsEntityNotFoundException() {
        MovieUpdateDTO movieUpdateDTO = new MovieUpdateDTO(1L, "Название фильма",
                2024, "Описание фильма", 1L, new BigDecimal("8.5"));
        doThrow(new EntityNotFoundException("Фильм", movieUpdateDTO.getId().toString()))
                .when(movieDao).update(any(Movie.class));
        assertThrows(EntityNotFoundException.class, () -> movieService.updateMovie(movieUpdateDTO));
        verify(movieDao, times(1)).update(any(Movie.class));
    }
    //Удаление несуществующего фильма
    @Test
    void testDeleteMovieThrowsEntityNotFoundException() {
        Long movieId = 1L;

        doThrow(new EntityNotFoundException("Фильм", movieId.toString())).when(movieDao).delete(movieId);

        assertThrows(EntityNotFoundException.class, () -> movieService.deleteMovie(movieId));

        verify(movieDao, times(1)).delete(movieId);
    }
}
