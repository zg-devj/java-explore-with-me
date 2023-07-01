package ru.practicum.mainservice.dtos;

import javax.validation.constraints.NotEmpty;

/**
 * Пользователь
 */
public class UserShortDto {
    // Идентификатор пользователя
    private Long id;

    // Имя
    @NotEmpty
    private String name;
}
