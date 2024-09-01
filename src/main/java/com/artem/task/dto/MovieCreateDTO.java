package com.artem.task.dto;

import java.math.BigDecimal;

public class MovieCreateDTO {
    private String title;
    private int releaseYear;
    private String description;
    private Long genreId;
    private BigDecimal rating;

    public MovieCreateDTO() {}

    public MovieCreateDTO(String title, int releaseYear, String description, Long genreId, BigDecimal rating) {
        this.title = title;
        this.releaseYear = releaseYear;
        this.description = description;
        this.genreId = genreId;
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
