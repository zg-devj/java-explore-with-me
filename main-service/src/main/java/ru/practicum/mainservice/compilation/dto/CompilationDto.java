package ru.practicum.mainservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import ru.practicum.mainservice.event.dto.EventShortDto;

import java.util.List;

/**
 * Подборка событий
 */
@Builder
@Getter
public class CompilationDto {
    // Идентификатор подборки
    private Long id;

    // Закреплена ли подборка на главной странице сайта
    private Boolean pinned;

    // Заголовок подборки
    private String title;

    // Список событий входящих в подборку
    private List<EventShortDto> events;
}
