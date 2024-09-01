package com.artem.task.dto;

import java.math.BigDecimal;
import java.util.List;

public class MovieDTO {
    private String title;
    private int releaseYear;
    private String description;
    private String genre;
    private List<ActorForMovieDTO> actors;
    private BigDecimal rating;

    public MovieDTO() {}

    public MovieDTO(String title, int releaseYear, String description, String genre, List<ActorForMovieDTO> actors, BigDecimal rating) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.genre = genre;
        this.actors = actors;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public List<ActorForMovieDTO> getActors() {
        return actors;
    }

    public void setActors(List<ActorForMovieDTO> actors) {
        this.actors = actors;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
