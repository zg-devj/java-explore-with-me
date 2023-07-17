package ru.practicum.mainservice.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.mainservice.category.dto.CategoryDto;
import ru.practicum.mainservice.user.dto.UserShortDto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Краткая информация о событии
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventDate;

    // Нужно ли оплачивать участие
    private Boolean paid;

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
