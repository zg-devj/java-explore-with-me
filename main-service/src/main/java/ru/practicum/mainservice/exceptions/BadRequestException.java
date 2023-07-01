package ru.practicum.mainservice.exceptions;

/**
 * Запрос составлен некорректно
 * Для кода 400
 */
public class BadRequestException extends RuntimeException {

    private final String response;

    public BadRequestException(String message, String response) {
        super(message);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
