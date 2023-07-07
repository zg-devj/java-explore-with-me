package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.mainservice.event.EventStateAction;

import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * Данные для изменения информации о событии.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Data
public class UpdateEventUserRequest {
    // Новая аннотация
    @Size(min = 20, max = 2000)
    @Null
    private String annotation;

    // Новая категория
    private Long category;

    // Новое описание
    @Size(min = 20, max = 7000)
    @Null
    private String description;

    // Новые дата и время на которые намечено событие.
    // Дата и время указываются в формате "yyyy-MM-dd HH:mm:ss"
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    // Широта и долгота места проведения события
    private Location location;

    // Новое значение флага о платности мероприятия
    private Boolean paid;

    // Новый лимит пользователей
    private Integer participantLimit;

    // Нужна ли пре-модерация заявок на участие
    private Boolean requestModeration;

    // Изменение сотояния события
    // EventStateAction [SEND_TO_REVIEW, CANCEL_REVIEW]
    private EventStateAction stateAction;

    // Новый заголовок
    @Size(min = 3, max = 120)
    private String title;
}
