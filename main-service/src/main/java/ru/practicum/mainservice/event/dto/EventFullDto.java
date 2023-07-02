package ru.practicum.mainservice.event.dto;

import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;

/**
 * Полная информация о событии
 */
public class EventFullDto {

    // Идентификатор события
    private Long id;

    // Краткое описание
    @NotNull
    private String annotation;

    // Категория
    @NotNull
    private CategoryDto category;

    // Количество одобренных заявок на участие в данном событии
    private Long confirmedRequests;

    // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    private String createdDate;

    // Полное описание события
    private String description;

    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    private String eventDate;

    // Пользователь (краткая информация)
    @NotNull
    private UserShortDto initiator;

    // Широта и долгота места проведения события
    @NotNull
    private Location location;

    @NotNull
    private boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Integer participantLimit;

    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    private String publishedOn;

    // Нужна ли пре-модерация заявок на участие
    // default false
    public boolean requestModeration;

    // Список состояний жизненного цикла события
    private EventState state;

    // Заголовок
    @NotNull
    private String title;

    // Количество просмотрев события
    private Long views;
}
