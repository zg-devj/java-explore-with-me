package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.event.EventState;
import ru.practicum.mainservice.user.dto.UserShortDto;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Полная информация о событии
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    // Идентификатор события
    private Long id;

    // Краткое описание
    @NotNull
    private String annotation;

    // Категория
    private CategoryDto category;

    // Количество одобренных заявок на участие в данном событии
    private Integer confirmedRequests;

    // Дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdOn;

    // Полное описание события
    private String description;

    // Дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    // Пользователь (краткая информация)
    private UserShortDto initiator;

    // Широта и долгота места проведения события
    private Location location;

    private boolean paid;

    // Ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    private Integer participantLimit;

    // Дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publishedOn;

    // Нужна ли пре-модерация заявок на участие
    // default false
    public boolean requestModeration;

    // Список состояний жизненного цикла события
    private EventState state;

    // Заголовок
    private String title;

    // Количество просмотрев события
    private Long views;
}
