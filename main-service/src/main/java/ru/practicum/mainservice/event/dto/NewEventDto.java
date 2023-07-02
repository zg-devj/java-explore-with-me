package ru.practicum.mainservice.event.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Новое событие
 */
public class NewEventDto {

    // Краткое описание события
    @NotEmpty
    @Min(20)
    @Max(2000)
    private String annotation;

    // id категории к которой относится событие
    @NotNull
    private Long category;

    // Полное описание события
    @NotNull
    @Min(20)
    @Max(7000)
    private String description;

    // Дата и время на которые намечено событие. Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @NotEmpty
    private String eventDate;

    // Широта и долгота места проведения события
    @NotNull
    private Location location;

    // Нужно ли оплачивать участие в событии, default = false
    private boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    // default false
    private Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие. Если true, то все заявки будут ожидать
    // подтверждения инициатором события. Если false - то будут подтверждаться автоматически
    // default true
    private boolean requestModeration;

    @NotEmpty
    @Min(3)
    @Max(120)
    // Заголовок события
    private String title;
}
