package com.artem.task.service;

import com.artem.task.dao.GenreDao;
import com.artem.task.dto.GenreDTO;
import com.artem.task.dto.GenreUpdateDTO;
import com.artem.task.exception.EntityAlreadyExistsException;
import com.artem.task.exception.GenreNotFoundException;
import com.artem.task.model.Genre;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GenreService {

    private final GenreDao genreDao;

    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    //CRUD
    //Вывод всех жанров
    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreDao.findAll();
        List<GenreDTO> genreDTOS = new ArrayList<>();
        for (Genre genre : genres) {
            GenreDTO genreDTO = new GenreDTO(
                    genre.getName()
            );
            genreDTOS.add(genreDTO);
        }
        return genreDTOS;
    }
    //Вывод жанра по id
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreDao.findById(id);
        return new GenreDTO(
                genre.getName()
        );
    }
    //Создание нового жанра
    public void saveGenre(GenreDTO genreDTO) {
        if (genreDao.findByName(genreDTO.getName()) != null) {
            throw new EntityAlreadyExistsException("Жанр", genreDTO.getName());
        }
        Genre genre = new Genre(
                genreDTO.getName()
        );
        genreDao.save(genre);
    }
    //Редактирование жанра
    public void updateGenre(GenreUpdateDTO genreUpdateDTO) {
        Genre genre = new Genre();
        genre.setId(genreUpdateDTO.getId());
        genre.setName(genreUpdateDTO.getName());
        genreDao.update(genre);
    }
    //Удаление жанра по id
    public void deleteGenre(Long id) {
        genreDao.delete(id);
    }
}
