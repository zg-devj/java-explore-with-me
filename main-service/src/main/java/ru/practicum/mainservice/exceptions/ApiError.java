package ru.practicum.mainservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

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

    // Список StackTrace
    private final List<String> errors;

    public ApiError(String status, String reason, String message, List<String> errors) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.errors = errors;
    }
}
