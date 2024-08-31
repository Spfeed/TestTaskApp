package com.artem.task.model;

public class MovieCast {
    private Long movieId;
    private Long actorId;
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
