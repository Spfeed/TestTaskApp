package com.artem.task.crud;

import com.artem.task.dao.MovieCastDao;
import com.artem.task.exception.MovieCastAlreadyExistsException;
import com.artem.task.exception.MovieCastNotFoundException;
import com.artem.task.model.MovieCast;
import com.artem.task.service.MovieCastService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MovieCastServiceTests {

    private MovieCastDao movieCastDao;
    private MovieCastService movieCastService;

    @BeforeEach
    void setUp() {
        movieCastDao = mock(MovieCastDao.class);
        movieCastService = new MovieCastService(movieCastDao);
    }

    //Поиск несуществующего персонажа
    @Test
    public void testGetMovieCastByMovieIdAndActorIdNotFound() {
        Long nonExistMovieId = 1L;
        Long nonExistActorId = 2L;
        when(movieCastDao.findByMovieIdAndActorId(nonExistMovieId, nonExistActorId))
                .thenReturn(null);
        assertThrows(MovieCastNotFoundException.class, () -> movieCastService.getCharacterByMovieIdAndActorId(nonExistMovieId, nonExistActorId));
        verify(movieCastDao, times(1)).findByMovieIdAndActorId(nonExistMovieId, nonExistActorId);
    }
    //Создание существующего персонажа
    @Test
    public void testSaveCharacterAlreadyExists() {
        MovieCast movieCast = new MovieCast(1L, 2L, "Имя персонажа");

        doThrow(new MovieCastAlreadyExistsException(movieCast.getMovieId(), movieCast.getActorId()))
                .when(movieCastDao).save(movieCast);

        assertThrows(MovieCastAlreadyExistsException.class, () -> movieCastService.saveCharacter(movieCast));
        verify(movieCastDao, times(1)).save(movieCast);
    }
    //Редактирование несуществующего персонажа
    @Test
    public void testUpdateCharacterNotFound() {
        MovieCast movieCast = new MovieCast(1L, 2L, "Имя персонажа");

        doThrow(new MovieCastNotFoundException(movieCast.getMovieId(), movieCast.getActorId()))
                .when(movieCastDao).update(movieCast);

        assertThrows(MovieCastNotFoundException.class, () -> movieCastService.updateCharacter(movieCast));
        verify(movieCastDao, times(1)).update(movieCast);
    }
    //Удаление несуществующего персонажа
    @Test
    public void testDeleteCharacterNotFound() {
        Long movieId = 1L;
        Long actorId = 2L;

        doThrow(new MovieCastNotFoundException(movieId, actorId))
                .when(movieCastDao).delete(movieId, actorId);

        assertThrows(MovieCastNotFoundException.class, () -> movieCastService.deleteCharacter(movieId, actorId));
        verify(movieCastDao, times(1)).delete(movieId, actorId);
    }
}
