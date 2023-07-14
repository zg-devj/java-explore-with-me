package ru.practicum.server.stats.exceptions;

import lombok.Getter;

@Getter
public class ErrorMessageField {

    private final String field;

    private final String message;

    public ErrorMessageField(String field, String message) {
        this.field = field;
        this.message = message;
    }

    public String getErrorMessage() {
        return "Field: " + field + ". Error: " + message + ". ";
    }
}
