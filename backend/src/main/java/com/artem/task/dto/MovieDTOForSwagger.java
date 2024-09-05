package com.artem.task.dto;

import java.math.BigDecimal;

public class MovieDTOForSwagger {
    private String title;
    private int releaseYear;
    private String description;
    private String genre;
    private BigDecimal rating;

    public MovieDTOForSwagger() {}

    public MovieDTOForSwagger(String title, int releaseYear, String description, String genre, BigDecimal rating) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.genre = genre;
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

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
