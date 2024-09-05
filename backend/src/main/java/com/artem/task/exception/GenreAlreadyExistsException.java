package com.artem.task.exception;

public class GenreAlreadyExistsException extends EntityAlreadyExistsException {
    public GenreAlreadyExistsException(Long id) {
        super("Жанр", id);
    }
}
