package ru.practicum.mainservice.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Данные нового пользователя
 */
@Builder
@Getter
@Setter
public class NewUserRequest {
    // Почтовый адрес (unique)
    @Size(min = 6, max = 256)
    @Email
    private String email;

    // Имя
    @NotBlank
    @Size(min = 2, max = 250)
    private String name;
}
