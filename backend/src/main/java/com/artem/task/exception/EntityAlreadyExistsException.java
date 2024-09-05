package com.artem.task.exception;

public class EntityAlreadyExistsException extends RuntimeException{
    public EntityAlreadyExistsException(String entityName, Object key) {
        super(String.format("%s с ключом '%s' уже существует.", entityName, key));
    }
}
