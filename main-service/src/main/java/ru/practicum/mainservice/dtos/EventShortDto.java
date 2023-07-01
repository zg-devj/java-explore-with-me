package ru.practicum.mainservice.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Краткая информация о событии
 */
public class EventShortDto {
    // Идентификатор события
    private Long id;

    // Краткое описание
    @NotEmpty
    private String annotation;

    // Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;

    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotEmpty
    private String eventDate;

    // Нужно ли оплачивать участие
    private boolean paid;

    // Заголовок
    @NotEmpty
    private String title;

    // Количество просмотрев события
    private Long views;

    @NotNull
    private CategoryDto category;

    @NotNull
    private UserShortDto initiator;
}
