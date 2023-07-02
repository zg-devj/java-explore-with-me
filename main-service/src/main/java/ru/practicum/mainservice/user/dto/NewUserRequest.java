package ru.practicum.mainservice.user.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Данные нового пользователя
 */
public class NewUserRequest {
    // Почтовый адрес (unique)
    @Min(6)
    @Max(256)
    private String email;

    // Имя
    @Min(2)
    @Max(250)
    private String name;
}
