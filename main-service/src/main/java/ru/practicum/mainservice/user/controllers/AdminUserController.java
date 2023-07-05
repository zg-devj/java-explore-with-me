package ru.practicum.mainservice.user.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.mainservice.user.UserService;
import ru.practicum.mainservice.user.dto.NewUserRequest;
import ru.practicum.mainservice.user.dto.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

/**
 * API для работы с пользователями
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final UserService userService;

    // Получение информации о пользователях
    @GetMapping
    public List<UserDto> adminGetUsers(
            @RequestParam(required = false) List<Long> ids,
            @RequestParam(required = false, defaultValue = "0") @PositiveOrZero int from,
            @RequestParam(required = false, defaultValue = "10") @PositiveOrZero int size
    ) {
        log.info("GET /admin/users?ids={}&from={}&size={} - Получение информации о пользователях.",
                ids, from, size);

        return null;
    }

    // Добавление нового пользователя
    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
    public UserDto adminAddUser(
            @RequestBody @Valid NewUserRequest newUserRequest
    ) {
        log.info("POST /admin/users - Добавление нового пользователя.");
        return userService.createUser(newUserRequest);
    }

    // Удаление пользователя
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void adminRemoveUser(
            @PathVariable long userId
    ) {
        log.info("DELETE /admin/users/{} - Удаление пользователя.", userId);
        userService.deleteUser(userId);
    }
}
