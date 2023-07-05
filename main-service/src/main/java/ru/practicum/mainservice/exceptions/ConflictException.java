package ru.practicum.mainservice.exceptions;

/**
 * Объект(объекты) уже существует
 * Для кода 409
 */
public class ConflictException extends RuntimeException {

    private final String reason;

    public ConflictException(String message, String reason) {
        super(message);
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }
}
