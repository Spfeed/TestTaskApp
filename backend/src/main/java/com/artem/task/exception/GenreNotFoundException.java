package com.artem.task.exception;

public class GenreNotFoundException extends EntityNotFoundException {
    public GenreNotFoundException(Long id) {
        super("Жанр", id);
    }
}
