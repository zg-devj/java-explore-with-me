package ru.practicum.mainservice.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * Сведения об ошибке
 */
@Getter
@AllArgsConstructor
public class ErrorMessage {

    // TODO: 01.07.2023 delete
//    // Список стектрейсов или описания ошибок
//    private List<String> errors;

    // Код статуса HTTP-ответа
    private String status;

    // Общее описание причины ошибки
    private String response;

    // Сообщение об ошибке
    private String message;

    // Дата и время когда произошла ошибка (в формате "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();
}
