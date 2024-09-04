package com.artem.task.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class MovieCreateDTO {
    @Size(min = 1, max = 255, message = "Название фильма должно быть длиной от 1 до 255 символов")
    private String title;
    @Min(value = 1888, message = "Год выпуска фильма должен быть не ранее 1888")
    @Max(value = 2100, message = "Год выпуска фильма должен быть не позднее 2100")
    private int releaseYear;
    @NotBlank(message = "Описание фильма не должно быть пустым")
    private String description;
    @NotNull(message = "Жанр должен быть указан")
    private Long genreId;
    @DecimalMin(value = "0.0", inclusive = false, message = "Рейтинг должен быть больше 0")
    @DecimalMax(value = "10.0", inclusive = false, message = "Рейтинг должен быть не более 10")
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
