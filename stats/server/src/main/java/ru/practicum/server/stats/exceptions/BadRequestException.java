package ru.practicum.server.stats.exceptions;

/**
 * Запрос составлен некорректно
 * Для кода 400
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(String message) {
        super(message);
    }
}
