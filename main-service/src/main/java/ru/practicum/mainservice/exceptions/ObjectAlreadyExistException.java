package ru.practicum.mainservice.exceptions;

/**
 * Объект(объекты) уже существует
 * Для кода 409
 */
public class ObjectAlreadyExistException extends RuntimeException {

    public ObjectAlreadyExistException(String message) {
        super(message);
    }
}
