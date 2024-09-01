package com.artem.task.controller;

import com.artem.task.dto.MovieCastDTO;
import com.artem.task.model.MovieCast;
import com.artem.task.service.MovieCastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/characters")
public class MovieCastController {

    private final MovieCastService movieCastService;

    public MovieCastController(MovieCastService movieCastService) {
        this.movieCastService = movieCastService;
    }

    //CRUD
    //Вывод персонажа по id фильма и id актера
    @GetMapping("/movie/{movieId}/actor/{actorId}")
    public ResponseEntity<MovieCastDTO> getCharacterByMovieIdAndActorId(
            @PathVariable Long movieId, @PathVariable Long actorId) {
        MovieCastDTO character = movieCastService.getCharacterByMovieIdAndActorId(movieId, actorId);
        return new ResponseEntity<>(character, HttpStatus.OK);
    }
    //Вывод персонажей фильма
    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<MovieCastDTO>> getCharactersByMovieId(
            @PathVariable Long movieId) {
        List<MovieCastDTO> chatacters = movieCastService.getCharactersByMovieId(movieId);
        return new ResponseEntity<>(chatacters, HttpStatus.OK);
    }
    //Вывод всех персонажей, сыгранных актером
    @GetMapping("/actor/{actorId}")
    public ResponseEntity<List<MovieCastDTO>> getCharacterByActorId(
            @PathVariable Long actorId) {
        List<MovieCastDTO> characters = movieCastService.getCharactersByActorId(actorId);
        return new ResponseEntity<>(characters, HttpStatus.OK);
    }
    //Создание нового персонажа
    @PostMapping
    public ResponseEntity<Void> createCharacter(@RequestBody MovieCast movieCast) {
        movieCastService.saveCharacter(movieCast);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    //Редактирование персонажа
    @PutMapping()
    public ResponseEntity<Void> updateCharacter(@RequestBody MovieCast movieCast) {
        movieCastService.updateCharacter(movieCast);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Удаление персонажа
    @DeleteMapping("/movie/{movieId}/actor/{actorId}")
    public ResponseEntity<Void> deleteCharacter(
            @PathVariable Long movieId, @PathVariable Long actorId) {
        movieCastService.deleteCharacter(movieId, actorId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
