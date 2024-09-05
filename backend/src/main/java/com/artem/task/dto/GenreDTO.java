package com.artem.task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GenreDTO {
    @Size(min = 2, max = 255, message = "Название жанра должно быть длиной от 2 до 255 символов")
    private String name;
    public GenreDTO() {}

    public GenreDTO(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
