package com.artem.task.model;

import java.math.BigDecimal;

public class Movie {
    private Long id;
    private String title;
    private Integer releaseYear;
    private String description;
    private Long genreId;
    private BigDecimal rating;

    public Movie(){}

    public Movie(String title, Integer releaseYear, String description, Long genreId, BigDecimal rating){
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.genreId = genreId;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void setRating(BigDecimal rating) {
        this.rating = rating;
    }
}
