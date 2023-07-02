package ru.practicum.mainservice.event.dto;

import ru.practicum.mainservice.event.EventStateAction;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Данные для изменения информации о событии.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
public class UpdateEventUserRequest {
    // Новая аннотация
    @Min(20)
    @Max(2000)
    private String annotation;

    // Новая категория
    private Integer category;

    // Новое описание
    @Min(20)
    @Max(7000)
    private String description;

    // Новые дата и время на которые намечено событие.
    // Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    private String eventDate;

    // Широта и долгота места проведения события
    private Location location;

    // Новое значение флага о платности мероприятия
    private boolean paid;

    // Новый лимит пользователей
    private Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие
    private boolean requestModeration;

    // Изменение сотояния события
    // EventStateAction [SEND_TO_REVIEW, CANCEL_REVIEW]
    private EventStateAction stateAction;

    // Новый заголовок
    @Min(3)
    @Max(120)
    private String title;
}
