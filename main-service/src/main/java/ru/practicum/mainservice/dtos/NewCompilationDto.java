package ru.practicum.mainservice.dtos;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Подборка событий
 */
public class NewCompilationDto {
    // Список идентификаторов событий входящих в подборку
    // unique, null
    private Set<Long> events;

    // Закреплена ли подборка на главной странице сайта
    // default false
    private boolean pinned;

    // Заголовок подборки
    @NotEmpty
    @Min(1)
    @Max(50)
    private String title;
}
