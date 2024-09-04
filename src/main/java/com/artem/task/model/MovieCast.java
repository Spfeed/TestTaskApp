package com.artem.task.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class MovieCast {
    @NotNull(message = "Id фильма не должно быть пустым")
    private Long movieId;
    @NotNull(message = "Id актера не должно быть пустым")
    private Long actorId;
    @Size(min = 1, max = 255, message = "Имя персонажа должно быть длиной от 1 до 255 символов")
    private String characterName;

    public MovieCast(){

    }

    public MovieCast(Long movieId, Long actorId, String characterName) {
        this.movieId = movieId;
        this.actorId = actorId;
        this.characterName = characterName;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getActorId() {
        return actorId;
    }

    public void setActorId(Long actorId) {
        this.actorId = actorId;
    }

    public String getCharacterName() {
        return characterName;
    }

    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
}
