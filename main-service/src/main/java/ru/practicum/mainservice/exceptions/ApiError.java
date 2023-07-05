package ru.practicum.mainservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
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

    // TODO: 01.07.2023 delete
//    // Список стектрейсов или описания ошибок
//    private List<String> errors;

    // Код статуса HTTP-ответа
    private String status;

    // Общее описание причины ошибки
    private String reason;

    // Сообщение об ошибке
    private String message;

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
