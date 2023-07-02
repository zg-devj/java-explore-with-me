package ru.practicum.mainservice.user.dto;

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
