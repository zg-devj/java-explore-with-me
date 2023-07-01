package ru.practicum.mainservice.exceptions;

/**
 * Ничего не найдено
 * Для кода 404
 */
public class NotFoundException extends RuntimeException {

    private final String response;

    public NotFoundException(String message, String response) {
        super(message);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}