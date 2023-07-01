package ru.practicum.mainservice.exceptions;

/**
 * Объект(объекты) уже существует
 * Для кода 409
 */
public class ObjectAlreadyExistException extends RuntimeException {
    private final String response;

    public ObjectAlreadyExistException(String message, String response) {
        super(message);
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
