package com.artem.task.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class GenreUpdateDTO {
    @NotNull(message = "Id жанра не должно быть пустым")
    private Long id;
    @Size(min = 2, max = 255, message = "Название жанра должно быть длиной от 2 до 255 символов")
    private String name;

    public GenreUpdateDTO() {}

    public GenreUpdateDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
