package com.artem.task.exception;

public class EntityNotFoundException extends RuntimeException{
    public EntityNotFoundException(String entityName, Object key) {
        super(String.format("%s с ключом '%s' не найден.", entityName, key));
    }
}
