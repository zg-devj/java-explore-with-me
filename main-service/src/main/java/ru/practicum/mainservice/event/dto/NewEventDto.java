package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

/**
 * Новое событие
 */
@Data
public class NewEventDto {

    // Краткое описание события
    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    // id категории к которой относится событие
    @NotNull
    @Positive
    private Long category;

    // Полное описание события
    @NotBlank
    @Size(min = 20, max = 7000)
    private String description;

    // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    // Широта и долгота места проведения события
    @NotNull
    private Location location;

    // Нужно ли оплачивать участие в событии, default = false
    private Boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @PositiveOrZero
    private Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать
    // подтверждения инициатором события. Если false - то будут подтверждаться автоматически
    // default true
    private Boolean requestModeration;

    // Заголовок события
    @NotBlank
    @Size(min = 3, max = 120)
    private String title;
}
