package ru.practicum.mainservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Подборка событий
 */
@Builder
@Getter
@Setter
public class NewCompilationDto {
    // Список идентификаторов событий входящих в подборку
    // unique, null
    private Set<Long> events;

    // Закреплена ли подборка на главной странице сайта
    // default false
    private boolean pinned;

    // Заголовок подборки
    @NotBlank
    @Size(min = 1, max = 50)
    private String title;
}
