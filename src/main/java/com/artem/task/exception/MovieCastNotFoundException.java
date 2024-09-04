package com.artem.task.exception;

public class MovieCastNotFoundException extends EntityNotFoundException {

    public MovieCastNotFoundException(Long movieId, Long actorId) {
        super("Персонаж", "Id фильма: " + movieId + ", Id актера: " + actorId);
    }
}
