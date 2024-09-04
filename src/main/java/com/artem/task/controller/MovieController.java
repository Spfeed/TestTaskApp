package com.artem.task.controller;

import com.artem.task.dto.MovieCreateDTO;
import com.artem.task.dto.MovieDTO;
import com.artem.task.dto.MovieDTOForSwagger;
import com.artem.task.dto.MovieUpdateDTO;
import com.artem.task.service.MovieService;
import jakarta.validation.Valid;
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
    public ResponseEntity<Void> createMovie(@RequestBody @Valid MovieCreateDTO movieCreateDTO) {
        movieService.saveMovie(movieCreateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Редактирование фильма
    @PutMapping()
    public ResponseEntity<Void> updateMovie(@RequestBody @Valid MovieUpdateDTO movieUpdateDTO) {
        movieService.updateMovie(movieUpdateDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Удаление фильма
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //Эндпоинты для задания
    //Вывод всех фильмов с детальной информацией
    @GetMapping("/details")
    public ResponseEntity<List<MovieDTO>> getAllMoviesDetailed() {
        List<MovieDTO> movies = movieService.getAllMoviesDetails();
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //Фильтрация на основании вхождения слова в название
    //Фильтрация на уровне сервиса
    @GetMapping("/serviceFiltration/{keyword}")
    public ResponseEntity<List<MovieDTO>> getMoviesFilteredByTitleService(@PathVariable String keyword) {
        List<MovieDTO> movies = movieService.filterMoviesByTitleJavaStream(keyword);
        if(movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Если нет фильмов с таким словом в названии, то возвращаем 402 статус-код
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //Фильтрация на уровне данных
    @GetMapping("/dataFiltration/{keyword}")
    public ResponseEntity<List<MovieDTO>> getMoviesFilteredByTitleData(@PathVariable String keyword) {
        List<MovieDTO> movies = movieService.filterMoviesByTitleMovieDao(keyword);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Аналогично 402, если фильмы с таким словом в названии не найдены
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //Фильтрация по жанрам
    @PostMapping("/filterByGenres")
    public ResponseEntity<List<MovieDTO>> getMoviesFilteredByGenres(@RequestBody List<String> genres) {
        List<MovieDTO> movies = movieService.getMoviesFilteredByGenres(genres);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Если фильмов с указанным(и) жанром(-ами) нет, то возвращаем 402 статус-код
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
    //Фильтрация по фамилии (имени и фамилии или имени актера)
    @GetMapping("/filterByActor/{actorName}")
    public ResponseEntity<List<MovieDTO>> getMoviesFilteredByActorName(@PathVariable String actorName) {
        List<MovieDTO> movies = movieService.getMoviesFilteredByActor(actorName);
        if (movies.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); //Если нет фильмов с указанным именем и фамилией (именем или фамилией) актера, то возвращаем 402 статус-код
        }
        return new ResponseEntity<>(movies, HttpStatus.OK);
    }
}
