package com.artem.task.crud;

import com.artem.task.dao.GenreDao;
import com.artem.task.dto.GenreDTO;
import com.artem.task.dto.GenreUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.EntityNotFoundException;
import com.artem.task.model.Genre;
import com.artem.task.service.GenreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class GenreServiceTests {

    private GenreService genreService;
    private GenreDao genreDao;

    @BeforeEach
    void setUp() {
        genreDao = mock(GenreDao.class);
        genreService = new GenreService(genreDao);
    }

    //Поиск несуществующего жанра
    @Test
    public void testGetGenreByIdNotFound() {
        Long nonExistGenreId = 999L;
        when(genreDao.findById(nonExistGenreId)).thenThrow(new EntityNotFoundException("Жанр", nonExistGenreId));
        assertThrows(EntityNotFoundException.class, () -> genreService.getGenreById(nonExistGenreId));
        verify(genreDao, times(1)).findById(nonExistGenreId);
    }
    //Попытка создания существующего актера
    @Test
    public void testSaveGenreAlreadyExists() {
        GenreDTO genreDTO = new GenreDTO("Название жанра");
        when(genreDao.findByName(genreDTO.getName())).thenReturn(new Genre());
        assertThrows(EntityAlreadyExistsException.class, () -> genreService.saveGenre(genreDTO));
        verify(genreDao, never()).save(any(Genre.class));
    }
    //Редактирование несуществующего жанра
    @Test
    public void testUpdateGenreNotFound() {
        GenreUpdateDTO genreUpdateDTO = new GenreUpdateDTO(999L, "Название жанра");
        doThrow(new EntityNotFoundException("Жанр", genreUpdateDTO.getId()))
                .when(genreDao).update(any(Genre.class));
        assertThrows(EntityNotFoundException.class, () -> genreService.updateGenre(genreUpdateDTO));
        verify(genreDao, times(1)).update(any(Genre.class));
    }
    //Удаление несуществующего жанра
    @Test
    public void testDeleteGenreNotFound() {
        Long nonExistGenreId = 999L;
        doThrow(new EntityNotFoundException("Жанр", nonExistGenreId))
                .when(genreDao).delete(nonExistGenreId);
        assertThrows(EntityNotFoundException.class, () -> genreService.deleteGenre(nonExistGenreId));
        verify(genreDao, times(1)).delete(nonExistGenreId);
    }
}
