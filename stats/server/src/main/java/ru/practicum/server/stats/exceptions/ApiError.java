package ru.practicum.server.stats.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Сведения об ошибке
 */
@Getter
@Setter
public class ApiError {

    // Код статуса HTTP-ответа
    private final String status;

    // Общее описание причины ошибки
    private final String reason;

    // Сообщение об ошибке e.getMessage()
    private final String message;

    // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    public ApiError(String status, String reason, String message) {
        this.status = status;
        this.reason = reason;
        this.message = message;
    }
}
