package ru.practicum.mainservice.user.dto;

import javax.validation.constraints.NotNull;

/**
 * Пользователь
 */
public class UserDto {
    // Идентификатор пользователя
    private Long id;

    // Почтовый адрес (unique)
    @NotNull
    private String email;

    // Имя
    @NotNull
    private String name;
}
