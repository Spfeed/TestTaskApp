package com.artem.task.controller;

import com.artem.task.dto.GenreDTO;
import com.artem.task.dto.GenreUpdateDTO;
import com.artem.task.model.Genre;
import com.artem.task.service.GenreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    //CRUD
    //Вывод всех жанров
    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        List<GenreDTO> genres = genreService.getAllGenres();
        return new ResponseEntity<>(genres, HttpStatus.OK);
    }
    //Вывод жанра по id
    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable Long id) {
        GenreDTO genre = genreService.getGenreById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Создание жанра
    @PostMapping
    public ResponseEntity<Void> createGenre(@RequestBody @Valid GenreDTO genreDTO) {
        genreService.saveGenre(genreDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Редактирование жанра
    @PutMapping()
    public ResponseEntity<Void> updateGenre(@RequestBody @Valid GenreUpdateDTO genreUpdateDTO) {
        genreService.updateGenre(genreUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Удаление жанра
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
        genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/findByName/{genreName}")
    public ResponseEntity<Genre> getGenreByName (@PathVariable String genreName){
        Genre genre = genreService.getGenreByName(genreName);
        return new ResponseEntity<>(genre, HttpStatus.OK);
    }
}
