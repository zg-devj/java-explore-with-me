package ru.practicum.mainservice.user.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotEmpty;

/**
 * Пользователь
 */
@Builder
@Getter
public class UserShortDto {
    // Идентификатор пользователя
    private Long id;

    // Имя
    @NotEmpty
    private String name;
}
