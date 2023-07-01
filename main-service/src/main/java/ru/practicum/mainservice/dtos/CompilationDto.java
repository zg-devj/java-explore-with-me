package ru.practicum.mainservice.dtos;

import java.util.ArrayList;
import java.util.List;

/**
 * Подборка событий
 */
public class CompilationDto {
    // Идентификатор подборки
    private Long id;

    // Закреплена ли подборка на главной странице сайта
    private boolean pinned;

    // Заголовок подборки
    private String title;

    // Список событий входящих в подборку
    private List<EventShortDto> events = new ArrayList<>();
}
