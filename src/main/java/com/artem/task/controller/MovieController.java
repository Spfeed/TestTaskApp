package com.artem.task.controller;

import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieDTOForSwagger;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.service.MovieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    //Получение всех фильмов
    @GetMapping
    public ResponseEntity<List<MovieDTOForSwagger>> getAllMovies() {
        List<MovieDTOForSwagger> movies = movieService.getAllMovies();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //Получение фильма по id
    @GetMapping("/{id}")
    public ResponseEntity<MovieDTOForSwagger> getMovieById(@PathVariable Long id) {
        MovieDTOForSwagger movie = movieService.getMovieById(id);
        return new ResponseEntity<>(movie, HttpStatus.OK);
    }
    //Создание нового фильма
    @PostMapping
    public ResponseEntity<Void> createMovie(@RequestBody MovieCreateDTO movieCreateDTO) {
        movieService.saveMovie(movieCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Редактирование фильма
    @PutMapping()
    public ResponseEntity<Void> updateMovie(@RequestBody MovieUpdateDTO movieUpdateDTO) {
        movieService.updateMovie(movieUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Удаление фильма
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
