package ru.practicum.mainservice.exceptions;

/**
 * Ничего не найдено
 * Для кода 404
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}