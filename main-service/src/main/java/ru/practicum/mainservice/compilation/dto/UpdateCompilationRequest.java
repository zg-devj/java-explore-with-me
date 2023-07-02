package ru.practicum.mainservice.compilation.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

/**
 * Изменение информации о подборке событий.
 * Если поле в запросе не указано (равно null) - значит изменение этих данных не требуется.
 */
public class UpdateCompilationRequest {

    // Список id событий подборки для полной замены текущего списка
    private Set<Long> events;

    // Закреплена ли подборка на главной странице сайта
    private boolean pinned;

    // Заголовок подборки
    @Min(1)
    @Max(50)
    private String title;
}
