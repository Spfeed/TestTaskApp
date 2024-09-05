package com.artem.task.exception;

public class MovieCastAlreadyExistsException extends EntityAlreadyExistsException {
    public MovieCastAlreadyExistsException(Long movieId, Long actorId) {
        super("Персонаж", "Id фильма: " + movieId + ", Id актера: " + actorId);
    }
}
