package ru.practicum.mainservice.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

/**
 * Пользователь
 */
@Builder
@Getter
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
