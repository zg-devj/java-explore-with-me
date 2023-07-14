package ru.practicum.mainservice.compilation.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Изменение информации о подборке событий.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
@Builder
@Getter
@Setter
public class UpdateCompilationRequest {

    // Список id событий подборки для полной замены текущего списка
    private Set<Long> events;

    // Закреплена ли подборка на главной странице сайта
    private boolean pinned;

    // Заголовок подборки
    @Size(min = 1, max = 50)
    private String title;
}
